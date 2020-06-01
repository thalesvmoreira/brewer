package com.thales.brewer.service.event.venda;

import com.thales.brewer.model.Cerveja;
import com.thales.brewer.model.ItemVenda;
import com.thales.brewer.repository.Cervejas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VendaListener {

    @Autowired
    private Cervejas cervejas;

    @EventListener
    public void vendaEmitida(VendaEvent vendaEvent){
        for(ItemVenda item : vendaEvent.getVenda().getItens()){
            Cerveja cerveja = cervejas.findOne(item.getCerveja().getId());
            cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());

            cervejas.save(cerveja);
        }
    }

}
