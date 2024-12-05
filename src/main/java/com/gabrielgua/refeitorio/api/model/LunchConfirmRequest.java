package com.gabrielgua.refeitorio.api.model;

import com.gabrielgua.refeitorio.domain.model.Price;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LunchConfirmRequest {

    @NotNull
    private UUID credential;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private BigDecimal final_price;
}