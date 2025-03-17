package com.gabrielgua.refeitorio.domain.exception;

public class AtendimentoNotFound extends ResourceNotFoundException{
    public AtendimentoNotFound(String message) {
        super(message);
    }
}