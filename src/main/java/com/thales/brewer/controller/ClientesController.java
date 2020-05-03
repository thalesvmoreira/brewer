package com.thales.brewer.controller;

import com.thales.brewer.model.Cliente;
import com.thales.brewer.model.TipoPessoa;
import com.thales.brewer.repository.Estados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private Estados estados;

    @RequestMapping("/novo")
    public ModelAndView novo(Cliente cliente){
        ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
        mv.addObject("tiposPessoa", TipoPessoa.values());
        mv.addObject("estados", estados.findAll());

        return mv;
    }

    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            return novo(cliente);
        }

        attributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso.");
        return new ModelAndView("redirect:/clientes/novo");
    }

}
