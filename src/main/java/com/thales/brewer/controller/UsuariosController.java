package com.thales.brewer.controller;

import com.thales.brewer.controller.page.PageWrapper;
import com.thales.brewer.model.Usuario;
import com.thales.brewer.repository.Grupos;
import com.thales.brewer.repository.Usuarios;
import com.thales.brewer.repository.filter.UsuarioFilter;
import com.thales.brewer.service.CadastroUsuarioService;
import com.thales.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.thales.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private Usuarios usuarios;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private Grupos grupos;

    @RequestMapping("/novo")
    public ModelAndView novo(Usuario usuario) {
        ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
        mv.addObject("grupos", grupos.findAll());

        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return novo(usuario);
        }

        try {
            cadastroUsuarioService.salvar(usuario);
        } catch(EmailUsuarioJaCadastradoException e){
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novo(usuario);
        } catch(SenhaObrigatoriaUsuarioException e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novo(usuario);
        }

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
        return new ModelAndView("redirect:/usuarios/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result,
                                  @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");

        PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable),
                httpServletRequest);
        mv.addObject("pagina", paginaWrapper);

        return mv;
    }
}