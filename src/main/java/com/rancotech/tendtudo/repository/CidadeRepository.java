package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
