package com.thales.brewer.service;

import com.thales.brewer.model.Cerveja;
import com.thales.brewer.repository.Cervejas;
import com.thales.brewer.service.event.cerveja.CervejaSalvaEvent;
import com.thales.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.thales.brewer.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;

@Service
public class CadastroCervejaService {

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private FotoStorage fotoStorage;

    @Transactional
    public void salvar(Cerveja cerveja){
        cervejas.save(cerveja);
        publisher.publishEvent(new CervejaSalvaEvent(cerveja));
    }

    @Transactional
    public void excluir(Cerveja cerveja){

        try{
            String foto = cerveja.getFoto();
            cervejas.delete(cerveja);
            cervejas.flush();
            fotoStorage.excluir(foto);
        } catch (PersistenceException e){
            throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda.");
        }

    }
}
