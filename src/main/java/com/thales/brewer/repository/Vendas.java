package com.thales.brewer.repository;

import com.thales.brewer.model.Venda;
import com.thales.brewer.repository.helper.venda.VendasQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {
}
