package com.gabrielgua.refeitorio.domain.filter;


import com.gabrielgua.refeitorio.domain.model.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialRangeFilter {

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}