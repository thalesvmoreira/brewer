package com.thales.brewer.service;

import com.thales.brewer.model.Estilo;
import com.thales.brewer.repository.Estilos;
import com.thales.brewer.service.exception.NomeEstiloJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroEstiloService {

    @Autowired
    private Estilos estilos;

    @Transactional
    public Estilo salvar(Estilo estilo){
        Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());

        if(estiloOptional.isPresent()){
            throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
        }

        return estilos.saveAndFlush(estilo);
    }
}
