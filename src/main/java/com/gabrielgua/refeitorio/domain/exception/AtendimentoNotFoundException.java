package com.gabrielgua.refeitorio.domain.exception;

public class AtendimentoNotFoundException extends ResourceNotFoundException{
    public AtendimentoNotFoundException(String message) {
        super(message);
    }
}