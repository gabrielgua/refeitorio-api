package com.gabrielgua.refeitorio.api.strategy.factory;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.strategy.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.model.ClientType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class OrderDiscountFactory {

    private final Map<ClientType, OrderDiscountStrategy> strategies = new HashMap<>();

    public OrderDiscountFactory(Set<OrderDiscountStrategy> strategySet) {
        strategySet.forEach(strategy -> strategies.put(strategy.getClientType(), strategy));
    }

    public OrderDiscountStrategy getStrategy(ClientType clientType) {
        var strategy = strategies.get(clientType);
        if (strategy == null) {
            throw new BusinessException("Couldn't find a suitable discount strategy.");
        }

        return strategy;
    }

}