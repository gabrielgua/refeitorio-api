package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceMovementResponse {

    private Long id;
    private String credential;

    @Enumerated(EnumType.STRING)
    private BalanceMovementType type;

    private BigDecimal amount;
    private BigDecimal oldBalance;
    private BigDecimal newBalance;

    private OffsetDateTime timestamp;
}