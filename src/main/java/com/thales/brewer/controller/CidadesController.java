package com.thales.brewer.controller;

import com.thales.brewer.controller.page.PageWrapper;
import com.thales.brewer.model.Cidade;
import com.thales.brewer.repository.Cidades;
import com.thales.brewer.repository.Estados;
import com.thales.brewer.repository.filter.CidadeFilter;
import com.thales.brewer.service.CadastroCidadeService;
import com.thales.brewer.service.exception.NomeCidadeJaCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    private Cidades cidades;

    @Autowired
    private Estados estados;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @RequestMapping("/nova")
    public ModelAndView nova(Cidade cidade){
        ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
        mv.addObject("estados", estados.findAll());

        return mv;
    }

    @Cacheable(value = "cidades", key = "#idEstado")
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarPorIdEstado(@RequestParam(name = "estado", defaultValue = "-1") Long idEstado){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        return cidades.findByEstadoId(idEstado);
    }

    @PostMapping("/nova")
    @CacheEvict(value = "cidades", key = "#cidade.estado.id", condition = "#cidade.temEstado()")
    public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes){

        if(result.hasErrors()){
            return nova(cidade);
        }

        try{
            cadastroCidadeService.salvar(cidade);
        } catch(NomeCidadeJaCadastradaException e){
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return nova(cidade);
        }


        attributes.addFlashAttribute("mensagem", "Cidade cadastrada com sucesso.");

        return new ModelAndView("redirect:/cidades/nova");
    }

    @GetMapping
    public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result
            , @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest){

        ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
        mv.addObject("estados", estados.findAll());

        PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidades.filtrar(cidadeFilter, pageable), httpServletRequest);
        mv.addObject("pagina", paginaWrapper);

        return mv;
    }

}
