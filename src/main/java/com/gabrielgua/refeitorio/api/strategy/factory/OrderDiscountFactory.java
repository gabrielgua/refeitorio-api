package com.gabrielgua.refeitorio.api.strategy.factory;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.strategy.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.model.DiscountType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class OrderDiscountFactory {

    private final Map<DiscountType, OrderDiscountStrategy> strategies = new HashMap<>();

    public OrderDiscountFactory(Set<OrderDiscountStrategy> strategySet) {
        strategySet.forEach(strategy -> strategies.put(strategy.getDiscountType(), strategy));
    }

    public OrderDiscountStrategy getStrategy(String credential) {
        var discountType = getDiscountType(Integer.parseInt(credential));
        return strategies.get(discountType);
    }

    private DiscountType getDiscountType(Integer credential) {
        var discountType = Arrays.stream(DiscountType.values()).filter(type -> type.applies(credential)).findFirst();
        if (discountType.isEmpty()) {
            throw new BusinessException("Couldn't find a suitable discount strategy for this credential.");
        }
        return discountType.get();
    }
}