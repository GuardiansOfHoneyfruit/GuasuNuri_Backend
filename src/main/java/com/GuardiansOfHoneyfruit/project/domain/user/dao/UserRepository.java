package com.GuardiansOfHoneyfruit.project.domain.user.dao;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
}
