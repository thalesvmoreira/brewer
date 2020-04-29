package com.thales.brewer.repository;

import com.thales.brewer.model.Estilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long> {

    public Optional<Estilo> findByNomeIgnoreCase(String nome);
}
