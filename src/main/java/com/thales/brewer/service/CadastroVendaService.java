package com.thales.brewer.service;

import com.thales.brewer.model.StatusVenda;
import com.thales.brewer.model.Venda;
import com.thales.brewer.repository.Vendas;
import com.thales.brewer.service.event.venda.VendaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CadastroVendaService {

    @Autowired
    private Vendas vendas;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public Venda salvar(Venda venda){

        if(venda.isSalvarProibido()){
            throw new RuntimeException("Usuário tentando salvar uma venda proibida");
        }

        if(venda.isNova()){
            venda.setDataCriacao(LocalDateTime.now());
        } else{
            Venda vendaExistente = vendas.findOne(venda.getId());
            venda.setDataCriacao(vendaExistente.getDataCriacao());
        }

        if(venda.getDataEntrega() != null){
            venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega()
                    , venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
        }

        return vendas.saveAndFlush(venda);
    }

    @Transactional
    public void emitir(Venda venda) {
        venda.setStatus(StatusVenda.EMITIDA);
        salvar(venda);

        publisher.publishEvent(new VendaEvent(venda));
    }

    @PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
    @Transactional
    public void cancelar(Venda venda) {
        Venda vendaExistente = vendas.findOne(venda.getId());

        vendaExistente.setStatus(StatusVenda.CANCELADA);
        vendas.save(vendaExistente);
    }
}
