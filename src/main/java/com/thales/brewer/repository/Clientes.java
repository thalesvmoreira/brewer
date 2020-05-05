package com.thales.brewer.repository;

import com.thales.brewer.model.Cliente;
import com.thales.brewer.repository.helper.cliente.ClientesQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

    public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);
}
