package com.gabrielgua.refeitorio.api.strategy;

import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.DiscountType;

import java.math.BigDecimal;

public interface OrderDiscountStrategy {

    BigDecimal getDiscount(Atendimento atendimento, Client client);
    DiscountType getDiscountType();
}