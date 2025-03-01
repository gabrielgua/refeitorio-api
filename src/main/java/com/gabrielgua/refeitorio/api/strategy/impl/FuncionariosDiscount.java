package com.gabrielgua.refeitorio.api.strategy.impl;

import com.gabrielgua.refeitorio.api.strategy.AtendimentoCodeValidator;
import com.gabrielgua.refeitorio.api.strategy.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.api.strategy.DiscountCredentialValidator;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FuncionariosDiscount implements OrderDiscountStrategy {

    private final DiscountCredentialValidator validator;
    private final AtendimentoCodeValidator atendimentoValidator;

    @Override
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
        validator.validate(client, this);

        var discount = BigDecimal.ZERO;
        var salary = client.getSalary();


        if (client.getFreeOfCharge()) {
            return BigDecimal.ONE;
        }

        if (atendimento.getCode().equals(AtendimentoCodeValidator.JANTAR)) {
            discount = BigDecimal.ONE;
        }

        if (atendimentoValidator.validate(atendimento, AtendimentoCodeValidator.ALMOCO)) {
            if (isSalaryInRange(salary, BigDecimal.ONE, BigDecimal.valueOf(1000))) {
                discount = BigDecimal.valueOf(0.7);
            }

            if (isSalaryInRange(salary, BigDecimal.valueOf(1000.01), BigDecimal.valueOf(2000))) {
                discount = BigDecimal.valueOf(0.5);
            }

            if (isSalaryInRange(salary, BigDecimal.valueOf(2000.01), BigDecimal.valueOf(5000))) {
                discount = BigDecimal.valueOf(0.2);
            }
        }

        return discount;
    }

    @Override
    public CredentialRange getCredentialRange() {
        return CredentialRange.FUNCIONARIOS;
    }

    private Boolean isSalaryInRange(BigDecimal salary, BigDecimal min, BigDecimal max) {
        return salary.compareTo(min) >= 0 && salary.compareTo(max) <= 0;
    }
}