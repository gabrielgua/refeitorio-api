package com.gabrielgua.refeitorio.api.model;

import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceMovementRequest {

    @NotNull
    private BigDecimal amount;
}