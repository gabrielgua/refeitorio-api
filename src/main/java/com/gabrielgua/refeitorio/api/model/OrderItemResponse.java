package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {

    private Long id;
    private Integer quantity;

    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal discountedPrice;
    private BigDecimal totalPrice;
    private BigDecimal weight;
    private ProductResponse product;

}