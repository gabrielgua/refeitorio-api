package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "order_items")
public class OrderItem {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    public void calculateTotalPrice() {
        var unitPrice = getUnitPrice();
        var quantity = getQuantity();

        if (unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }

        if (quantity == null) {
            quantity = 0;
        }

        setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
    }
}