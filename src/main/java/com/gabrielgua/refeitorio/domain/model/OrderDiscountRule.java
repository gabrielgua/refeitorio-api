package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_discount_rules")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDiscountRule {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_discount_strategy_id")
    private OrderDiscountStrategy discountStrategy;

    @ManyToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderDiscountType discountType;
    private BigDecimal discountValue;
}