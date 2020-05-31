package com.thales.brewer.repository;

import com.thales.brewer.model.Usuario;
import com.thales.brewer.repository.helper.usuario.UsuariosQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

    public Optional<Usuario> findByEmailOrId(String email, Long id);

    public List<Usuario> findByIdIn(Long[] codigos);
}
