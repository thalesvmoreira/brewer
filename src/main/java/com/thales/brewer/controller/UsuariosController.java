package com.thales.brewer.controller;

import com.thales.brewer.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UsuariosController {

    @RequestMapping("/usuarios/novo")
    public String novo(Usuario usuario){
        return "usuario/CadastroUsuario";
    }

    @RequestMapping(value = "/usuarios/novo", method = RequestMethod.POST)
    public String cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return novo(usuario);
        }

        attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso.");
        return "redirect:/usuarios/novo";
    }

}
