package com.thales.brewer.repository.helper.venda;

import com.thales.brewer.model.Venda;
import com.thales.brewer.repository.filter.VendaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendasQueries {
    public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);

    public Venda buscarComItens(Long id);

}
