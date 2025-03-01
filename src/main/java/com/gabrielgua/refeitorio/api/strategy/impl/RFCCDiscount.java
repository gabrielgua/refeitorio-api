package com.gabrielgua.refeitorio.api.strategy.impl;

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
public class RFCCDiscount implements OrderDiscountStrategy {

    private final DiscountCredentialValidator validator;

    @Override
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
        validator.validate(client, this);
        return BigDecimal.ZERO; //0% discount for RFCC
    }

    @Override
    public CredentialRange getCredentialRange() {
        return CredentialRange.RFCC;
    }
}