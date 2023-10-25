package com.GuardiansOfHoneyfruit.project.domain.region.exception;

import com.GuardiansOfHoneyfruit.project.global.error.exception.EntityNotFoundException;

public class PnuNotFoundException extends EntityNotFoundException {

    public PnuNotFoundException(String targetId){
        super(targetId + "is not found");
    }

}
