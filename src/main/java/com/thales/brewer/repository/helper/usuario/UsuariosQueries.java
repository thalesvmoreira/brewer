package com.thales.brewer.repository.helper.usuario;

import com.thales.brewer.model.Usuario;
import com.thales.brewer.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuariosQueries {

    public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);

    public Optional<Usuario> porEmailEAtivo(String email);

    public List<String> permissoes(Usuario usuario);

    public Usuario buscarComGrupos(Long id);
}
