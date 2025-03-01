package com.gabrielgua.refeitorio.api.strategy.factory;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.strategy.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class OrderDiscountFactory {

    private final Map<CredentialRange, OrderDiscountStrategy> strategies = new HashMap<>();

    public OrderDiscountFactory(Set<OrderDiscountStrategy> strategySet) {
        strategySet.forEach(strategy -> strategies.put(strategy.getCredentialRange(), strategy));
    }

    public OrderDiscountStrategy getStrategy(String credential) {
        var credentialRange = getCredentialRange(Integer.parseInt(credential));
        return strategies.get(credentialRange);
    }

    private CredentialRange getCredentialRange(Integer credential) {
        var credentialRange = Arrays.stream(CredentialRange.values())
                .filter(range -> range.applies(credential))
                .findFirst();

        if (credentialRange.isEmpty()) {
            throw new BusinessException("Couldn't find a suitable discount strategy for this credential.");
        }

        return credentialRange.get();
    }
}