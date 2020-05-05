package com.thales.brewer.controller;

import com.thales.brewer.controller.page.PageWrapper;
import com.thales.brewer.model.Cliente;
import com.thales.brewer.model.TipoPessoa;
import com.thales.brewer.repository.Clientes;
import com.thales.brewer.repository.Estados;
import com.thales.brewer.repository.filter.ClienteFilter;
import com.thales.brewer.service.CadastroClienteService;
import com.thales.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private Estados estados;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @Autowired
    private Clientes clientes;

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

        try {
            cadastroClienteService.salvar(cliente);
        } catch(CpfCnpjClienteJaCadastradoException e){
            result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
            return novo(cliente);
        }

        attributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso.");
        return new ModelAndView("redirect:/clientes/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult result,
                                  @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");

        PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(clientes.filtrar(clienteFilter, pageable),
                httpServletRequest);
        mv.addObject("pagina", paginaWrapper);

        return mv;
    }

}
