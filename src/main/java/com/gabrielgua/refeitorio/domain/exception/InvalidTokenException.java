package com.gabrielgua.refeitorio.domain.exception;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException(String message) {
        super(message);
    }
}