package com.gabrielgua.refeitorio.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemRequest {
    @NotNull
    private String productCode;
    private Integer quantity;
    private BigDecimal weight;
}