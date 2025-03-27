package com.gabrielgua.refeitorio.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String email) {
        super(String.format("User not found for email: %s", email));
    }

    public UserNotFoundException(Long userId) {
        super(String.format("User not found for id: %d", userId));
    }
}