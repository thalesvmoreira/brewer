package com.thales.brewer.repository.helper.cerveja;

import com.thales.brewer.model.Cerveja;
import com.thales.brewer.repository.filter.CervejaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CervejasQueries {

    public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);

}
