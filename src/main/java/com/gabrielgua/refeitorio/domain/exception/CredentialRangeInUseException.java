package com.gabrielgua.refeitorio.domain.exception;

public class CredentialRangeInUseException extends BusinessException {

    public CredentialRangeInUseException() {
        super("Cannot delete this CredentialRange because it is associated with one or more clients.");
    }

    public CredentialRangeInUseException(String message) {
        super(message);
    }
}