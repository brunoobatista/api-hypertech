package com.rancotech.tendtudo.repository;

import com.rancotech.tendtudo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByNomeIsNotLike(String root);
    List<Role> findAllByNomeNotContains(String root);
}
