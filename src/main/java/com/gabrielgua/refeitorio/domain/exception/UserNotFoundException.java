package com.gabrielgua.refeitorio.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(UUID credential) {
        super(String.format("User not found for credential: %s", credential));
    }
}