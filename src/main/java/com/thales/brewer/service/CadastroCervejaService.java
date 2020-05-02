package com.thales.brewer.service;

import com.thales.brewer.model.Cerveja;
import com.thales.brewer.repository.Cervejas;
import com.thales.brewer.service.event.cerveja.CervejaSalvaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCervejaService {

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public void salvar(Cerveja cerveja){
        cervejas.save(cerveja);
        publisher.publishEvent(new CervejaSalvaEvent(cerveja));
    }
}
