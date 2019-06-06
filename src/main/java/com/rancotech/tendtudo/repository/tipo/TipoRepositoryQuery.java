package com.rancotech.tendtudo.repository.tipo;

import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.repository.filter.TipoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoRepositoryQuery {

    public Page<Tipo> filtrar(TipoFilter tipoFilter, Pageable pageable);
    List<Tipo> filtrarPorTipo(String valor);

}
