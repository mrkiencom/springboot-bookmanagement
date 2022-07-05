package com.novahub.javatrain.javaspringbookmanagement.repositories;

import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(String name);
}
