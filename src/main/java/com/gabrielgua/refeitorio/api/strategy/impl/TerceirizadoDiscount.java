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
public class TerceirizadoDiscount implements OrderDiscountStrategy {

    private DiscountCredentialValidator credentialValidator;

    @Override
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
        credentialValidator.validate(client, this);
        return BigDecimal.ZERO;
    }

    @Override
    public CredentialRange getCredentialRange() {
        return CredentialRange.TERCEIRIZADO;
    }
}