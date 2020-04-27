package com.thales.brewer.controller;

import com.thales.brewer.model.Estilo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EstilosController {

    @RequestMapping("/estilos/novo")
    public String novo(Estilo estilo){
        return "estilo/CadastroPadraoEstilo";
    }

    @RequestMapping(value="/estilos/novo", method = RequestMethod.POST)
    public String cadastrar(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes){

        if(result.hasErrors()){
            return novo(estilo);
        }

        attributes.addFlashAttribute("mensagem", "Estilo cadastrado com sucesso.");
        return "redirect:/estilos/novo";
    }
}
