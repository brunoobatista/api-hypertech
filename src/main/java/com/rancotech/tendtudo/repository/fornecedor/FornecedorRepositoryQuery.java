package com.rancotech.tendtudo.repository.fornecedor;

import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.repository.filter.FornecedorFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorRepositoryQuery {

    Page<Fornecedor> filtrar(FornecedorFilter fornecedorFilter, Pageable pageable);

}
