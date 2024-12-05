package com.gabrielgua.refeitorio.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Price {
    private BigDecimal discount;
    private BigDecimal final_price;
    private BigDecimal original_price;
    private BigDecimal discounted_price;
}