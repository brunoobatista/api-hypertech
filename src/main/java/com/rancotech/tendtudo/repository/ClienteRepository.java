package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.repository.cliente.ClienteRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery {

    List<Cliente> findByNomeContainsIgnoreCase(String valor);

}
