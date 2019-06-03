package com.rancotech.tendtudo.repository.usuario;

import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UsuarioRepositoryQuery {

    Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);

}
