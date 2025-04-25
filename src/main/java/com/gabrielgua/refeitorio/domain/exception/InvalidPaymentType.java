package com.gabrielgua.refeitorio.domain.exception;

public class InvalidPaymentType extends BusinessException {
    public InvalidPaymentType() {
        super("The selected client does not use 'balance' as a payment method.");
    }
}