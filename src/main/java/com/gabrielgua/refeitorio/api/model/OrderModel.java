package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderModel {

    private Long id;
    private String number;
    private UserModel user;
    private AtendimentoModel atendimento;
    private List<OrderItemModel> items;
    private BigDecimal finalPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private OffsetDateTime createdAt;
}