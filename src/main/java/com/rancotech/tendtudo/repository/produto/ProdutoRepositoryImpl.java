package com.rancotech.tendtudo.repository.produto;

import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.filter.ProdutoFilter;
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

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
        Root<Produto> root = criteria.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
        criteria.where(predicates);

        criteria.orderBy(builder.asc(root.get("id")));

        TypedQuery<Produto> query = manager.createQuery(criteria);

        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(produtoFilter));
    }

    private Predicate[] criarRestricoes(ProdutoFilter produtoFilter, CriteriaBuilder builder,
                                        Root<Produto> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(produtoFilter.getNome())) {
            predicates.add(builder.like(
                    builder.lower(root.get("nome")), "%" + produtoFilter.getNome().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(produtoFilter.getTipoId())) {
            predicates.add(builder.equal(
               root.get("tipo"), produtoFilter.getTipoId()
            ));
        }

        Predicate p = builder.and(
                builder.equal(root.get("ativo"), StatusAtivo.ATIVADO.ordinal())
        );

        predicates.add(p);

        int size = predicates.size();
        return predicates.toArray(new Predicate[size]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(ProdutoFilter produtoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Produto> root = criteria.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

}
