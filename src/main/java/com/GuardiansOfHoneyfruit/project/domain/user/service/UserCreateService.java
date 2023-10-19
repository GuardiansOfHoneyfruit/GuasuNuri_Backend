package com.GuardiansOfHoneyfruit.project.domain.user.service;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserRepository;
import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCreateService {

    private final UserRepository userRepository;

    public User createUser(String email){
        String uuid = UUID.randomUUID().toString();

        return userRepository.save(User.builder()
                .userUuid(uuid)
                .email(email)
                .role("ROLE_USER")
                .build());
    }
}
