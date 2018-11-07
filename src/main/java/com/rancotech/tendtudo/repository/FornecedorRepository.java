package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.repository.fornecedor.FornecedorRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery {

}
