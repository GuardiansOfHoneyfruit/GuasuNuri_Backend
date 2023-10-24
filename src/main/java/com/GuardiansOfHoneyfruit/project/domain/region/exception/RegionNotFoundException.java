package com.GuardiansOfHoneyfruit.project.domain.region.exception;

import com.GuardiansOfHoneyfruit.project.global.error.exception.EntityNotFoundException;

public class RegionNotFoundException extends EntityNotFoundException {

    public RegionNotFoundException(String targetId){
        super(targetId + "is not found");
    }

}
