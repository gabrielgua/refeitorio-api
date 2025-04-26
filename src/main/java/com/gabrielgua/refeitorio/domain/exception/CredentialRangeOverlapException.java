package com.gabrielgua.refeitorio.domain.exception;

public class CredentialRangeOverlapException extends BusinessException {
    public CredentialRangeOverlapException() {
        super("One or more credential ranges conflict with the specified interval (min-max).");
    }
}