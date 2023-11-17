package com.GuardiansOfHoneyfruit.project.domain.user.dao;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserUuid(String userId);

    @Query("SELECT u.region.regionCode FROM User u WHERE u.userUuid = ?1")
    Optional<String> findUserRegionCodeByUserUuid(String userUuid);
}
