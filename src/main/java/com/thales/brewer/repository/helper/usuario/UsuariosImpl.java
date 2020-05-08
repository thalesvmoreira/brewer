package com.thales.brewer.repository.helper.usuario;

import com.thales.brewer.model.Grupo;
import com.thales.brewer.model.Usuario;
import com.thales.brewer.model.UsuarioGrupo;
import com.thales.brewer.repository.filter.UsuarioFilter;
import com.thales.brewer.repository.paginacao.PaginacaoUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UsuariosImpl implements UsuariosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;


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

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);

        paginacaoUtil.preparar(criteria, pageable);
        adicionarFiltro(filtro, criteria);

        List<Usuario> filtrados = criteria.list();
        filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));

        return new PageImpl<>(filtrados, pageable, total(filtro));
    }

    private Long total(UsuarioFilter filtro) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
        adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }


    private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }

            if (!StringUtils.isEmpty(filtro.getEmail())) {
                criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
            }

            if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
                List<Criterion> subqueries = new ArrayList<>();
                for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
                    DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
                    dc.add(Restrictions.eq("id.grupo.id", codigoGrupo));
                    dc.setProjection(Projections.property("id.usuario"));

                    subqueries.add(Subqueries.propertyIn("id", dc));
                }

                Criterion[] criterions = new Criterion[subqueries.size()];
                criteria.add(Restrictions.and(subqueries.toArray(criterions)));
            }
        }
    }
}
