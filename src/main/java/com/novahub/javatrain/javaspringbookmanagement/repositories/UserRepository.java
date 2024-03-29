package com.novahub.javatrain.javaspringbookmanagement.repositories;

import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
}
