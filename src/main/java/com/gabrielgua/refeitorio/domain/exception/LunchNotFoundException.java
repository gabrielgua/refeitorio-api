package com.gabrielgua.refeitorio.domain.exception;

public class LunchNotFoundException extends ResourceNotFoundException{
    public LunchNotFoundException(Long lunchId) {
        super(String.format("Lunch not found for id: %d", lunchId));
    }
}