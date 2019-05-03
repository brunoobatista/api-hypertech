package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);

}
