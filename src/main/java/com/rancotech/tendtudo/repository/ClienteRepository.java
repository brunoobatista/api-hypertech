package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.cliente.ClienteRepositoryQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery {

    List<Cliente> findByNomeContainsIgnoreCaseAndAtivoEquals(String valor, StatusAtivo ativo);
    boolean existsByEmail(String email);
    boolean existsByCpfCnpj(String cpfCnpj);

    @Query(
            value = "SELECT * FROM cliente ORDER BY id",
            countQuery = "SELECT count(*) FROM cliente",
            nativeQuery = true)
    Page<Cliente> selectByIdFirstAndSecondItem(Pageable pageable);

    @Query(
            value = "SELECT * FROM cliente WHERE ativo = ?3 ORDER BY id LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Cliente> selectClienteNextPage(int limit, int offset, int ativo);

}
