package com.thales.brewer.repository;

import com.thales.brewer.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cidades extends JpaRepository<Cidade, Long> {

    public List<Cidade> findByEstadoId(Long idEstado);
}
