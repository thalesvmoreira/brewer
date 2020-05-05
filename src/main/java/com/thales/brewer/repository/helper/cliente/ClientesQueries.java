package com.thales.brewer.repository.helper.cliente;

import com.thales.brewer.model.Cliente;
import com.thales.brewer.repository.filter.ClienteFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientesQueries {

    public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);

}
