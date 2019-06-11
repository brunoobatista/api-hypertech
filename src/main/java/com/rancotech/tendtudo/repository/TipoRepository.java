package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.tipo.TipoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rancotech.tendtudo.model.Tipo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TipoRepository extends JpaRepository<Tipo, Long>, TipoRepositoryQuery {

    Optional<Tipo> findByIdAndAtivoEquals(Long id, StatusAtivo ativo);

    @Query(
            value = "SELECT * FROM tipo WHERE ativo = ?3 ORDER BY id LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Tipo> selectTipoNextPage(int limit, int offset, Integer ativo);

}
