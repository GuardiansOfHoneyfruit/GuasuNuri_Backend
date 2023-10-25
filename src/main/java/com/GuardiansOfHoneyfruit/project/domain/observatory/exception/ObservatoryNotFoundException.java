package com.GuardiansOfHoneyfruit.project.domain.observatory.exception;


import com.GuardiansOfHoneyfruit.project.global.error.exception.EntityNotFoundException;

public class ObservatoryNotFoundException extends EntityNotFoundException {
    public ObservatoryNotFoundException(Long target){super(target + "is not Found");}
}
