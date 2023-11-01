package com.GuardiansOfHoneyfruit.project.domain.observatory.exception;

import com.GuardiansOfHoneyfruit.project.global.error.exception.InvalidArgumentException;

public class InvalidTokenLengthException extends InvalidArgumentException {
    public InvalidTokenLengthException(int tokenLength, int needLength){
        super("Invalid token length. Expected" +  needLength + "but got " + tokenLength);
    }

}
