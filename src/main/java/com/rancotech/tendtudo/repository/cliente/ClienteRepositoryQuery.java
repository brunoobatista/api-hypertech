package com.rancotech.tendtudo.repository.cliente;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.repository.filter.ClienteFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepositoryQuery {

    Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);

}
