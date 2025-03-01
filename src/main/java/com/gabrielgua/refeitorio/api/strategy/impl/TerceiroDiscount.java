package com.gabrielgua.refeitorio.api.strategy.impl;

import com.gabrielgua.refeitorio.api.strategy.DiscountCredentialValidator;
import com.gabrielgua.refeitorio.api.strategy.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TerceiroDiscount implements OrderDiscountStrategy {

    private final DiscountCredentialValidator validator;

    @Override
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
        validator.validate(client, this);
        return BigDecimal.ZERO;
    }

    @Override
    public CredentialRange getCredentialRange() {
        return CredentialRange.TERCEIROS;
    }
}