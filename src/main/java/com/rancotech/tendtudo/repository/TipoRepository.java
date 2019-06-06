package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.repository.tipo.TipoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rancotech.tendtudo.model.Tipo;

import java.util.List;

public interface TipoRepository extends JpaRepository<Tipo, Long>, TipoRepositoryQuery {

}
