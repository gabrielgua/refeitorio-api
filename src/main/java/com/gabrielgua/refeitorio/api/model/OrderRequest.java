package com.gabrielgua.refeitorio.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotBlank
    private String credential;
    @NotNull
    private Long atendimentoId;

    @NotNull
    private List<OrderItemRequest> items;
}