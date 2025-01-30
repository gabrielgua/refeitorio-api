package com.gabrielgua.refeitorio.api.strategy.impl;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.strategy.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.api.strategy.StrategyTypeValidator;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FuncionarioCLTDiscount implements OrderDiscountStrategy {

    private final StrategyTypeValidator validator;

    @Override
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
        var discount = BigDecimal.ZERO;
        validator.validate(client, this);

        var salary = client.getSalary();
        validateSalary(salary);

        if (atendimento.getName().equals("Jantar")) {
            discount = BigDecimal.ONE;
        }

        if (atendimento.getName().equals("Almoço")) {
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
    public ClientType getClientType() {
        return ClientType.FUNCIONARIO_CLT_E_APRENDIZ;
    }

    private void validateSalary(BigDecimal salary) {
        if (salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Salary cannot be negative!");
        }
    }

    private Boolean isSalaryInRange(BigDecimal salary, BigDecimal min, BigDecimal max) {
        return salary.compareTo(min) >= 0 && salary.compareTo(max) <= 0;
    }
}