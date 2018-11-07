package com.rancotech.tendtudo.repository.fornecedor;

import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.repository.filter.FornecedorFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorRepositoryQuery {

    public Page<Fornecedor> filtrar(FornecedorFilter fornecedorFilterFilter, Pageable pageable);

}
