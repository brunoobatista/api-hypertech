package com.rancotech.tendtudo.repository.fornecedor;

import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.filter.FornecedorFilter;
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

public class FornecedorRepositoryImpl implements FornecedorRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Fornecedor> filtrar(FornecedorFilter fornecedorFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Fornecedor> criteria = builder.createQuery(Fornecedor.class);
        Root<Fornecedor> root = criteria.from(Fornecedor.class);

        Predicate[] predicates = criarRestricoes(fornecedorFilter, builder, root);
        criteria.where(predicates);

        criteria.orderBy(builder.asc(root.get("id")));

        TypedQuery<Fornecedor> query = manager.createQuery(criteria);

        adicionarRestricoesDePaginacao(query, pageable);


        return new PageImpl<>(query.getResultList(), pageable, total(fornecedorFilter));
    }

    private Predicate[] criarRestricoes(FornecedorFilter fornecedorFilter, CriteriaBuilder builder,
                                        Root<Fornecedor> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(fornecedorFilter.getValorDeBusca())) {
            Predicate predicate1 = builder.like(
                    builder.lower(root.get("nome")), "%" + fornecedorFilter.getValorDeBusca().toLowerCase() + "%"
            );

            Predicate predicate2 = builder.like(
                    builder.lower(root.get("nomeFantasia")), "%" + fornecedorFilter.getValorDeBusca().toLowerCase() + "%"
            );

            Predicate predicate3 = builder.like(
                    builder.lower(root.get("cpfOuCnpj")), "%" + fornecedorFilter.getValorDeBusca().toLowerCase() + "%"
            );

            Predicate predicateOr = builder.or(predicate1, predicate2, predicate3);
            predicates.add(predicateOr);
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

    private Long total(FornecedorFilter fornecedorFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Fornecedor> root = criteria.from(Fornecedor.class);

        Predicate[] predicates = criarRestricoes(fornecedorFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

}
