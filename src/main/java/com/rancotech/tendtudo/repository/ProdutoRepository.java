package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.repository.produto.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

    List<Produto> findByNomeContainsIgnoreCaseOrderById(String valor);

}
