package com.thales.brewer.controller;

import com.thales.brewer.model.Cidade;
import com.thales.brewer.repository.Cidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    private Cidades cidades;

    @RequestMapping("/novo")
    public String novo(Cidade cidade){
        return "cidade/CadastroCidade";
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarPorIdEstado(@RequestParam(name = "estado", defaultValue = "-1") Long idEstado){
        return cidades.findByEstadoId(idEstado);
    }

    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public String cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes){

        if(result.hasErrors()){
            return novo(cidade);
        }

        attributes.addFlashAttribute("mensagem", "Cidade cadastrada com sucesso.");
        return "redirect:/cidades/novo";
    }

}
