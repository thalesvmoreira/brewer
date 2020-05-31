package com.thales.brewer.service.event.cerveja;

import com.thales.brewer.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CervejaListener {

    @Autowired
    private FotoStorage fotoStorage;

    @EventListener(condition = "#evento.foto and #evento.novaFoto")
    public void cervejaSalva(CervejaSalvaEvent evento){
        fotoStorage.salvar(evento.getCerveja().getFoto());
    }
}
