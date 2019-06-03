package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.repository.usuario.UsuarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {

    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    List<Usuario> findByNomeContainsIgnoreCase(String valor);

    @Query(
            value = "SELECT * FROM usuario ORDER BY id LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Usuario> selectClienteNextPage(int limit, int offset);

}
