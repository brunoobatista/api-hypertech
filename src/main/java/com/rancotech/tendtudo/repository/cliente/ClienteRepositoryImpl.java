package com.rancotech.tendtudo.repository.cliente;

import com.rancotech.tendtudo.model.Cliente;
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
            predicates.add(builder.like(
                    builder.lower(root.get("nome")), "%" + clienteFilter.getNome().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(clienteFilter.getEmail())) {
            predicates.add(builder.like(
                    builder.lower(root.get("nome")),clienteFilter.getEmail().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(clienteFilter.getCpfCnpj())) {
            predicates.add(builder.like(
                    builder.lower(root.get("nome")),clienteFilter.getCpfCnpj() + "%"));
        }

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
