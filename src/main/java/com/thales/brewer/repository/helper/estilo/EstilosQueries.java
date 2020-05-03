package com.thales.brewer.repository.helper.estilo;

import com.thales.brewer.model.Estilo;
import com.thales.brewer.repository.filter.EstiloFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstilosQueries {

    public Page<Estilo> filtrar(EstiloFilter filtro, Pageable pageable);

}
