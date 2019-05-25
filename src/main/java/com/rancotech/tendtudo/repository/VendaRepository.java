package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.repository.venda.VendaRepositoryImpl;
import com.rancotech.tendtudo.repository.venda.VendaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery {
}
