package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.produto.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

    List<Produto> findByNomeContainsIgnoreCaseAndAtivoEqualsOrderById(String valor, StatusAtivo ativo);
    Optional<Produto> findByIdAndAtivoEquals(Long id, StatusAtivo ativo);

    @Query(
            value = "SELECT * FROM produto p WHERE p.ativo = ?3 ORDER BY p.id LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    List<Produto> selectProdutoNextPage(int limit, int offset, Integer ativo);

}
