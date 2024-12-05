package com.gabrielgua.refeitorio.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LunchRequest {

    @NotNull
    private UUID credential;

    @NotNull
    private BigDecimal weight;
}