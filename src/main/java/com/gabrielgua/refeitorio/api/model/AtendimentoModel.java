package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.AtendimentoType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtendimentoModel {

    private Long id;
    private String name;
    private AtendimentoType type;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}