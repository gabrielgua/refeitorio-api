package com.gabrielgua.refeitorio.api.strategy.impl;

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
public class FuncionarioSNDDiscount implements OrderDiscountStrategy {

    private final StrategyTypeValidator validator;

    @Override
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
        validator.validate(client, this);
        return BigDecimal.ONE; //100% discount for Funcionario SND Produção
    }

    @Override
    public ClientType getClientType() {
        return ClientType.FUNCIONARIO_SND_PRODUCAO;
    }
}