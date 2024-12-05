package com.gabrielgua.refeitorio.domain.exception;

import com.gabrielgua.refeitorio.api.exception.BusinessException;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}