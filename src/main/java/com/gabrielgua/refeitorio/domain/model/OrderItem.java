package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "order_items")
public class OrderItem {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal weight;
    private Integer quantity;
    private BigDecimal unitPrice;

    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal totalPrice;
    private BigDecimal discountedPrice;


    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    public void calculateTotalPriceZeroDiscount() {
        setSubtotal(getBaseCalculation());
        setTotalPrice(getSubtotal());
        setDiscountedPrice(BigDecimal.ZERO);
        setDiscount(BigDecimal.ZERO);
    }

    public void calculateTotalPriceFreeOfCharge() {
        var discount = BigDecimal.ONE;
        var baseCalculation = getBaseCalculation();
        setSubtotal(baseCalculation);
        setDiscountedPrice(getSubtotal().multiply(discount));

        setDiscount(discount);
        setTotalPrice(getSubtotal().subtract(getDiscountedPrice()));
    }

    public void calculateTotalPriceByRule(OrderDiscountRule discountRule) {
        var discountedPrice = BigDecimal.ZERO;
        var baseCalculation = getBaseCalculation();
        setSubtotal(baseCalculation);

        if (discountRule.getDiscountValue() != null) {
            discountedPrice = discountRule.getDiscountValue();
        }

        if (discountRule.getDiscountType().equals(OrderDiscountType.PERCENTAGE_VALUE)) {
            setDiscountedPrice(getSubtotal().multiply(discountedPrice));
            setDiscount(discountedPrice);
        }
        
        if (discountRule.getDiscountType().equals(OrderDiscountType.FIXED_VALUE)) {
            setDiscountedPrice(discountedPrice);
            BigDecimal discount = getSubtotal().subtract(getTotalPrice());
            BigDecimal discountPercentage = discount.divide(getSubtotal(), 2, RoundingMode.HALF_UP);

            setDiscountedPrice(discountedPrice);
            setDiscount(discountPercentage);
        }

        setTotalPrice(getSubtotal().subtract(getDiscountedPrice()));
    }

    private BigDecimal getBaseCalculation() {
        var unitPrice = getUnitPrice();
        var quantity = getQuantity();
        var weight = getWeight();

        if (unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }

        if (quantity == null) {
            quantity = 0;
        }

        if (weight == null) {
            weight = BigDecimal.ONE;
        }

        if (!product.getAllowMultiple()) {
            quantity = 1;
        }

        return unitPrice.multiply(new BigDecimal(quantity)).multiply(weight);
    }
}