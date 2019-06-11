package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {

    Optional<VendaProduto> deleteByVendaIdAndProdutoId(Long vendaId, Long produtoId);
    Optional<VendaProduto> findByVendaIdAndProdutoId(Long vendaId, Long produtoId);

}
