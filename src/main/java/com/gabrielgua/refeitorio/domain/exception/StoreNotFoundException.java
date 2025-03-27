package com.gabrielgua.refeitorio.domain.exception;

public class StoreNotFoundException extends ResourceNotFoundException {
    public StoreNotFoundException(Long storeId) {
        super("Store not found for id: " + storeId);
    }
}