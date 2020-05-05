package com.thales.brewer.repository;

import com.thales.brewer.model.Cidade;
import com.thales.brewer.model.Estado;
import com.thales.brewer.repository.helper.cidade.CidadesQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

    public List<Cidade> findByEstadoId(Long idEstado);
    public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);

}
