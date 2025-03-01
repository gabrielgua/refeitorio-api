package com.gabrielgua.refeitorio.domain.exception;

public class ClientBalanceLimitReachedException extends BusinessException {
    public ClientBalanceLimitReachedException() {
        super("Client balance cannot be less than the limit of R$ -100,00");
    }
}