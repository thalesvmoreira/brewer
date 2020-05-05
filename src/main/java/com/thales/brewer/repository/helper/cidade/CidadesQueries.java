package com.thales.brewer.repository.helper.cidade;

import com.thales.brewer.model.Cidade;
import com.thales.brewer.repository.filter.CidadeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CidadesQueries {

    public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable);

}
