package com.rancotech.tendtudo.repository.tipo;

import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.filter.TipoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TipoRepositoryImpl implements TipoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Tipo> filtrarPorTipo(String valor) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Tipo> criteria = builder.createQuery(Tipo.class);
        Root<Tipo> root = criteria.from(Tipo.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(valor)) {
            predicates.add(builder.like(
                    builder.lower(root.get("nome")), "%" + valor.toLowerCase() + "%"));
        }

        Predicate p = builder.and(
                builder.equal(root.get("ativo"), StatusAtivo.ATIVADO.ordinal())
        );
        predicates.add(p);

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        criteria.orderBy(builder.asc(root.get("id")));

        TypedQuery<Tipo> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public Page<Tipo> filtrar(TipoFilter tipoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Tipo> criteria = builder.createQuery(Tipo.class);
        Root<Tipo> root = criteria.from(Tipo.class);

        Predicate[] predicates = criarRestricoes(tipoFilter, builder, root);
        criteria.where(predicates);

        criteria.orderBy(builder.asc(root.get("id")));


        TypedQuery<Tipo> query = manager.createQuery(criteria);

        adicionarRestricoesDePaginacao(query, pageable);


        return new PageImpl<>(query.getResultList(), pageable, total(tipoFilter));
    }

    private Predicate[] criarRestricoes(TipoFilter tipoFilter, CriteriaBuilder builder,
                                        Root<Tipo> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(tipoFilter.getNome())) {
            predicates.add(builder.like(
                    builder.lower(root.get("nome")), "%" + tipoFilter.getNome().toLowerCase() + "%"));
        }

        Predicate p = builder.and(
                builder.equal(root.get("ativo"), StatusAtivo.ATIVADO.ordinal())
        );
        predicates.add(p);

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(TipoFilter tipoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Tipo> root = criteria.from(Tipo.class);

        Predicate[] predicates = criarRestricoes(tipoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

}
