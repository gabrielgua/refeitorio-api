package com.gabrielgua.refeitorio.api.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    };
}