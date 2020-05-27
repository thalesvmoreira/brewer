package com.thales.brewer.service;

import com.thales.brewer.model.StatusVenda;
import com.thales.brewer.model.Venda;
import com.thales.brewer.repository.Vendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CadastroVendaService {

    @Autowired
    private Vendas vendas;

    @Transactional
    public Venda salvar(Venda venda){
        if(venda.isNova()){
            venda.setDataCriacao(LocalDateTime.now());
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
    }
}
