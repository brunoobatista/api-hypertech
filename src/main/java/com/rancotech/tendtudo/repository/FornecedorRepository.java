package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.fornecedor.FornecedorRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery {

    Optional<Fornecedor> findAllByIdAndAtivoEquals(Long id, StatusAtivo ativo);

    @Query(
            value = "SELECT * FROM fornecedor WHERE ativo = ?3 ORDER BY id LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    List<Fornecedor> selectFornecedorNextPage(int limit, int offset, Integer ativo);

}
