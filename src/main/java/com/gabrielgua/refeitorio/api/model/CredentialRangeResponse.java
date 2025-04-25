package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialRangeResponse {

    private Long id;
    private String name;
    private Integer min;
    private Integer max;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private OffsetDateTime createdAt;

}