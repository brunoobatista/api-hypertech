package com.rancotech.tendtudo.repository.cliente;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.model.enumerated.TipoPessoa;
import com.rancotech.tendtudo.repository.filter.ClienteFilter;
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

public class ClienteRepositoryImpl implements ClienteRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
        Root<Cliente> root = criteria.from(Cliente.class);

        Predicate[] predicates = criarRestricoes(clienteFilter, builder, root);
        criteria.where(predicates);

        criteria.orderBy(builder.asc(root.get("id")));

        TypedQuery<Cliente> query = manager.createQuery(criteria);

        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(clienteFilter));
    }

    private Predicate[] criarRestricoes(ClienteFilter clienteFilter, CriteriaBuilder builder,
                                        Root<Cliente> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(clienteFilter.getNome())) {
            Predicate p1 = builder.like(
                    builder.lower(root.get("nome")), "%" + clienteFilter.getNome().toLowerCase() + "%"
            );

            Predicate p2 = builder.like(
                    builder.lower(root.get("cpfCnpj")), "%" + clienteFilter.getNome().toLowerCase() + "%"
            );

            Predicate p3 = builder.like(
                    builder.lower(root.get("email")), "%" + clienteFilter.getNome().toLowerCase() + "%"
            );

            predicates.add(builder.or(p1, p2, p3));
        }
        if (!StringUtils.isEmpty(clienteFilter.getTipoPessoa())) {
            TipoPessoa tp;
            if (TipoPessoa.FISICA.toString().equals(clienteFilter.getTipoPessoa()) ) {
                tp = TipoPessoa.FISICA;
            } else {
                tp = TipoPessoa.JURIDICA;
            }
            Predicate p4 = builder.equal(
                    root.get("tipoPessoa"), tp
            );
            predicates.add(builder.and(p4));
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

    private Long total(ClienteFilter clienteFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Cliente> root = criteria.from(Cliente.class);

        Predicate[] predicates = criarRestricoes(clienteFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }


}
