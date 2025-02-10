package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultClientResponse {

    @JsonProperty("MATRICULA")
    private String credential;

    @JsonProperty("NOME")
    private String name;

    @JsonProperty("SALARIO")
    private BigDecimal salary;

    @JsonProperty("CARGO")
    private String role;
}