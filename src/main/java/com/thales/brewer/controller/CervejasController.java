package com.thales.brewer.controller;

import com.thales.brewer.controller.page.PageWrapper;
import com.thales.brewer.dto.CervejaDTO;
import com.thales.brewer.model.Cerveja;
import com.thales.brewer.model.Origem;
import com.thales.brewer.model.Sabor;
import com.thales.brewer.repository.Cervejas;
import com.thales.brewer.repository.Estilos;
import com.thales.brewer.repository.filter.CervejaFilter;
import com.thales.brewer.service.CadastroCervejaService;
import com.thales.brewer.service.exception.ImpossivelExcluirEntidadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/cervejas")
public class CervejasController {

    @Autowired
    private Estilos estilos;

    @Autowired
    private CadastroCervejaService cadastroCervejaService;

    @Autowired
    private Cervejas cervejas;

    @RequestMapping("/nova")
    public ModelAndView nova(Cerveja cerveja) {
        ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("estilos", estilos.findAll());
        mv.addObject("origens", Origem.values());
        return mv;
    }

    @RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
    public ModelAndView salvar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return nova(cerveja);
        }

        cadastroCervejaService.salvar(cerveja);
        attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");
        return new ModelAndView("redirect:/cervejas/nova");
    }

    @GetMapping
    public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result
            , @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
        mv.addObject("estilos", estilos.findAll());
        mv.addObject("sabores", Sabor.values());
        mv.addObject("origens", Origem.values());

        PageWrapper<Cerveja> paginaWrapper = new PageWrapper<>(cervejas.filtrar(cervejaFilter, pageable)
                , httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
        return mv;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome) {
        return cervejas.porSkuOuNome(skuOuNome);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Cerveja cerveja) {
        try {
            cadastroCervejaService.excluir(cerveja);
        } catch (ImpossivelExcluirEntidadeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ModelAndView editar(@PathVariable("id") Cerveja cerveja) {
        ModelAndView mv = nova(cerveja);
        mv.addObject(cerveja);
        return mv;
    }
}
