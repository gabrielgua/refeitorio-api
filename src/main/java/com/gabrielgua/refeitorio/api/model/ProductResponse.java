package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.PriceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private Long id;
    private String code;
    private String name;
    @JsonFormat(pattern = "0.00")
    private BigDecimal price;
    private PriceType priceType;
    private Boolean allowMultiple;
}