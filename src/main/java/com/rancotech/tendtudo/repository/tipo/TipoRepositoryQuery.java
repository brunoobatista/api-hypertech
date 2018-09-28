package com.rancotech.tendtudo.repository.tipo;

import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.repository.filter.TipoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TipoRepositoryQuery {

    public Page<Tipo> filtrar(TipoFilter tipoFilter, Pageable pageable);

}
