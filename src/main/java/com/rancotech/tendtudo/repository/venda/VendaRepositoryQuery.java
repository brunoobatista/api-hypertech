package com.rancotech.tendtudo.repository.venda;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.repository.filter.VendaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendaRepositoryQuery {

    Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);

}
