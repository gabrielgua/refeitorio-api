package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialRangeRequest {

    @NotBlank
    private String name;
    @NotNull
    private Integer min;
    @NotNull
    private Integer max;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}