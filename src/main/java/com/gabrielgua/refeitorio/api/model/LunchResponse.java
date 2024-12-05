package com.gabrielgua.refeitorio.api.model;

import com.gabrielgua.refeitorio.domain.model.Price;
import com.gabrielgua.refeitorio.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class LunchResponse {

    private Long id;
    private User user;
    private Price price;
    private BigDecimal weight;
    private OffsetDateTime created_at;
}