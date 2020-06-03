package com.thales.brewer.controller;

import com.thales.brewer.controller.page.PageWrapper;
import com.thales.brewer.controller.validator.VendaValidator;
import com.thales.brewer.dto.VendaMes;
import com.thales.brewer.mail.Mailer;
import com.thales.brewer.model.*;
import com.thales.brewer.repository.Cervejas;
import com.thales.brewer.repository.Vendas;
import com.thales.brewer.repository.filter.VendaFilter;
import com.thales.brewer.security.UsuarioSistema;
import com.thales.brewer.service.CadastroVendaService;
import com.thales.brewer.session.TabelasItensSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vendas")
public class VendasController {

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private TabelasItensSession tabelaItens;

    @Autowired
    private CadastroVendaService cadastroVendaService;

    @Autowired
    private VendaValidator vendaValidator;

    @Autowired
    private Vendas vendas;

    @Autowired
    private Mailer mailer;

    @GetMapping("/nova")
    public ModelAndView nova(Venda venda){
        ModelAndView mv = new ModelAndView("venda/CadastroVenda");

        setUid(venda);

        mv.addObject("itens", venda.getItens());
        mv.addObject("valorFrete", venda.getValorFrete());
        mv.addObject("valorDesconto", venda.getValorDesconto());
        mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));

        return mv;
    }

    @PostMapping(value = "/nova", params = "salvar")
    public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
        validarVenda(venda, result);

        if(result.hasErrors()){
            return nova(venda);
        }

        venda.setUsuario(usuarioSistema.getUsuario());

        cadastroVendaService.salvar(venda);
        attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");

        return new ModelAndView("redirect:/vendas/nova");
    }

    @PostMapping(value = "/nova", params = "emitir")
    public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
        validarVenda(venda, result);

        if(result.hasErrors()){
            return nova(venda);
        }

        venda.setUsuario(usuarioSistema.getUsuario());

        cadastroVendaService.emitir(venda);
        attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso");

        return new ModelAndView("redirect:/vendas/nova");
    }

    @PostMapping(value = "/nova", params = "enviarEmail")
    public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
        validarVenda(venda, result);

        if(result.hasErrors()){
            return nova(venda);
        }

        venda.setUsuario(usuarioSistema.getUsuario());

        venda = cadastroVendaService.salvar(venda);
        mailer.enviar(venda);

        attributes.addFlashAttribute("mensagem", String.format("Venda nº %d salva com sucesso e e-mail enviado", venda.getId()));

        return new ModelAndView("redirect:/vendas/nova");
    }

    @PostMapping("/item")
    public ModelAndView adicionarItem(Long idCerveja, String uuid){
        Cerveja cerveja = cervejas.getOne(idCerveja);
        tabelaItens.adicionarItem(uuid, cerveja, 1);
        return mvTabelaItensVenda(uuid);
    }

    @PutMapping("/item/{idCerveja}")
    public ModelAndView alterarQuantidadeItem(@PathVariable("idCerveja") Cerveja cerveja
            , Integer quantidade, String uuid){
        tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);

        return mvTabelaItensVenda(uuid);
    }

    @DeleteMapping("/item/{uuid}/{idCerveja}")
    public ModelAndView excluirItem(@PathVariable("idCerveja") Cerveja cerveja, @PathVariable String uuid){
        tabelaItens.excluirItem(uuid, cerveja);
        return mvTabelaItensVenda(uuid);
    }

    @GetMapping
    public ModelAndView pesquisar(VendaFilter vendaFilter,
                                  @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView("venda/PesquisaVendas");
        mv.addObject("todosStatus", StatusVenda.values());
        mv.addObject("tiposPessoa", TipoPessoa.values());

        PageWrapper<Venda> paginaWrapper = new PageWrapper<>(vendas.filtrar(vendaFilter, pageable)
                , httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView editar(@PathVariable Long id){
        Venda venda = vendas.buscarComItens(id);

        setUid(venda);
        for(ItemVenda item : venda.getItens()){
            tabelaItens.adicionarItem(venda.getUuid(), item.getCerveja(), item.getQuantidade());
        }

        ModelAndView mv = nova(venda);
        mv.addObject(venda);

        return mv;
    }

    @PostMapping(value = "/nova", params = "cancelar")
    public ModelAndView cancelar(Venda venda, BindingResult result, RedirectAttributes attributes,
                                 @AuthenticationPrincipal UsuarioSistema usuarioSistema){
        try{
            cadastroVendaService.cancelar(venda);
        } catch (AccessDeniedException e){
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("status", 403);

            return mv;
        }

        attributes.addFlashAttribute("mensagem", "Venda cancelada com sucesso");

        return new ModelAndView("redirect:/vendas/" + venda.getId());
    }

    @GetMapping("/totalPorMes")
    public @ResponseBody List<VendaMes> listarTotalVendaPorMes(){
        return vendas.totalPorMes();
    }

    private ModelAndView mvTabelaItensVenda(String uuid) {
        ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
        mv.addObject("itens", tabelaItens.getItens(uuid));
        mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));

        return mv;
    }

    private void validarVenda(Venda venda, BindingResult result) {
        venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
        venda.calcularValorTotal();

        vendaValidator.validate(venda, result);
    }

    private void setUid(Venda venda) {
        if(StringUtils.isEmpty(venda.getUuid())) {
            venda.setUuid(UUID.randomUUID().toString());
        }
    }
}
