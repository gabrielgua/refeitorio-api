package com.gabrielgua.refeitorio.domain.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}