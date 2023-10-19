package com.GuardiansOfHoneyfruit.project.domain.user.exception;

import com.GuardiansOfHoneyfruit.project.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String target){
        super(target + "is not found");
    }
}
