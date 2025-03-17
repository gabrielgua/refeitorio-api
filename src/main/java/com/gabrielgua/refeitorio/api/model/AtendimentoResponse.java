package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.PriceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtendimentoResponse {

    private Long id;
    private String name;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}