package com.gabrielgua.refeitorio.domain.model;

import lombok.Getter;

@Getter
public enum PaymentType {
    BALANCE_DEBIT("Saldo em conta"),
    PAYROLL_DEBIT("Pagamento em folha"),
    COURTESY("Cortesia");

    private final String label;

    PaymentType(String label) {
        this.label = label;
    }

}