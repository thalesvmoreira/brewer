package com.thales.brewer.repository;

import com.thales.brewer.model.Cerveja;
import com.thales.brewer.repository.helper.cerveja.CervejasQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>, CervejasQueries {

}