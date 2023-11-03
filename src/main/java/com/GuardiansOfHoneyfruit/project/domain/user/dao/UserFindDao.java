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
    private final UserCustomRepository userCustomRepository;

    public User findByUserUuId(String uuid){
        final Optional<User> user = userRepository.findByUserUuid(uuid);
        user.orElseThrow(() -> new UserNotFoundException(uuid));
        return user.get();
    }

    public Optional<User> findOptionalUserByEmail(String email){
        final Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    public boolean isUserRegionNull(String userUuid){
        return userCustomRepository.isUserRegionNull(userUuid);
    }
}
