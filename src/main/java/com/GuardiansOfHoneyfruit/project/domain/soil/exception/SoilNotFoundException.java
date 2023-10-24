package com.GuardiansOfHoneyfruit.project.domain.soil.exception;


import com.GuardiansOfHoneyfruit.project.global.error.exception.EntityNotFoundException;

public class SoilNotFoundException extends EntityNotFoundException {

    public SoilNotFoundException(Long target) {super(target + "is Not Found");}

}
