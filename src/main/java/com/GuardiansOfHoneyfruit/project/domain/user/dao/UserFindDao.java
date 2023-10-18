package com.GuardiansOfHoneyfruit.project.domain.user.dao;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import com.GuardiansOfHoneyfruit.project.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFindDao {

    private final UserRepository userRepository;

    public User findByUserId(String uuid){
        final Optional<User> user = userRepository.findByUserId(uuid);
        user.orElseThrow(() -> new UserNotFoundException(uuid));
        return user.get();
    }

}
