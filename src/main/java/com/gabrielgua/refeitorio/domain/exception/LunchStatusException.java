package com.gabrielgua.refeitorio.domain.exception;

import com.gabrielgua.refeitorio.api.exception.BusinessException;

public class LunchStatusException extends BusinessException {
    public LunchStatusException(String message) {
        super(message);
    }
}