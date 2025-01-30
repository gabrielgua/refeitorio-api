package com.gabrielgua.refeitorio.api.strategy;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.domain.model.Client;
import org.springframework.stereotype.Component;

@Component
public class StrategyTypeValidator {

    private Boolean isClientTypeEligibleForStrategy(Client client, OrderDiscountStrategy strategy) {
        var credential = Integer.parseInt(client.getCredential());
        var clientType = client.getType();
        var strategyType = strategy.getClientType();

        return clientType.equals(strategyType) && strategyType.isCredentialEligible(credential);
    }

    public void validate(Client client, OrderDiscountStrategy strategy) {
        if (!isClientTypeEligibleForStrategy(client, strategy)) {
            throw new BusinessException("Client credential doesn't suit this client type.");
        }
    }
}