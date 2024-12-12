package com.gabrielgua.refeitorio.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String credential) {
        super(String.format("User not found for credential: %s", credential));
    }
}