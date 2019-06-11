package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.venda.VendaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery {

    List<Venda> findAllByAtivoEquals(StatusAtivo ativo);
    Optional<Venda> findByIdAndAtivoEquals(Long id, StatusAtivo ativo);

    @Query(
            value = "SELECT * FROM venda WHERE ativo = ?3 ORDER BY id LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Venda> selectVendaNextPage(int limit, int offset, int ativo);

}
