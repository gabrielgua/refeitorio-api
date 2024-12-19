package com.gabrielgua.refeitorio.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    @NotNull
    private String productCode;
    @NotNull
    private Integer quantity;
}