package com.rancotech.tendtudo.repository.venda;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.dashboard.TotalVendasMes;
import com.rancotech.tendtudo.repository.filter.VendaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VendaRepositoryQuery {

    Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);

    List<TotalVendasMes> getTotalVendas();

}
