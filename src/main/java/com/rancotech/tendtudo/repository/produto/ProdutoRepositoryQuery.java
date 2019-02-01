package com.rancotech.tendtudo.repository.produto;

import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.repository.filter.ProdutoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoRepositoryQuery {

    Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable);

}
