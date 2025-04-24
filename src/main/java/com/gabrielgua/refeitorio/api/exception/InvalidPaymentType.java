package com.gabrielgua.refeitorio.api.exception;

import com.gabrielgua.refeitorio.domain.exception.BusinessException;

public class InvalidPaymentType extends BusinessException {
    public InvalidPaymentType() {
        super("The selected client does not use 'balance' as a payment method.");
    }
}