package com.thales.brewer.repository.helper.usuario;

import com.thales.brewer.model.Usuario;
import com.thales.brewer.repository.filter.UsuarioFilter;
import com.thales.brewer.repository.paginacao.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


public class UsuariosImpl implements UsuariosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Usuario> porEmailEAtivo(String email) {
        return manager
                .createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
                .setParameter("email", email).getResultList().stream().findFirst() ;
    }

    @Override
    public List<String> permissoes(Usuario usuario) {
        return manager.createQuery(
            "select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario"
            , String.class)
            .setParameter("usuario", usuario)
            .getResultList();
    }
}
