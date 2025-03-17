package com.gabrielgua.refeitorio.domain.exception;

public class CredentialRangeNotFound extends ResourceNotFoundException {
    public CredentialRangeNotFound(String message) {
        super(message);
    }
}