package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {

    private BigDecimal discount;
    private BigDecimal finalPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private List<OrderItemModel> items;
}