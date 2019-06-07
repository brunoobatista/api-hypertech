package com.rancotech.tendtudo.repository.tipo;

import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.filter.TipoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TipoRepositoryQuery {

    Page<Tipo> filtrar(TipoFilter tipoFilter, Pageable pageable);
    List<Tipo> filtrarPorTipo(String valor);

}
