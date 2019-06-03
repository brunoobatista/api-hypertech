package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByNomeIsNotLike(String root);
    List<Role> findAllByNomeNotContains(String root);
}
