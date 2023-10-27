package com.GuardiansOfHoneyfruit.project.global.error.exception;

public class InvalidArgumentException extends BusinessException{
    public InvalidArgumentException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
}
