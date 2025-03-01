package com.gabrielgua.refeitorio.domain.repository;

import com.gabrielgua.refeitorio.domain.model.OrderDiscountRule;
import com.gabrielgua.refeitorio.domain.model.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDiscountRuleRepository extends JpaRepository<OrderDiscountRule, Long> {

    Optional<OrderDiscountRule> findByDiscountStrategyAndProduct(OrderDiscountStrategy discountStrategy, Product product);
}