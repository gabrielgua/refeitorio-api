package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.OrderDiscountRule;
import com.gabrielgua.refeitorio.domain.model.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.model.Product;
import com.gabrielgua.refeitorio.domain.repository.OrderDiscountRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDiscountRuleService {

    private final OrderDiscountRuleRepository repository;

    @Transactional(readOnly = true)
    public Optional<OrderDiscountRule> findByDiscountStrategyAndProduct(OrderDiscountStrategy discountStrategy, Product product) {
        return repository.findByDiscountStrategyAndProduct(discountStrategy, product);
    }
}