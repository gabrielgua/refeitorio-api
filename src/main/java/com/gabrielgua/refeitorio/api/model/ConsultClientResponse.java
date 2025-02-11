package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultClientResponse {

    @JsonProperty("id")
    private String credential;

    @JsonProperty("NOME")
    private String name;

    @JsonProperty("SALARIO")
    private BigDecimal salary;

    @JsonProperty("CARGO")
    private String role;
}