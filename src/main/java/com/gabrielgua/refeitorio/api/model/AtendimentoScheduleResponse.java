package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtendimentoScheduleResponse {

    private boolean serving;
    private AtendimentoModel current;
    private AtendimentoModel previous;
    private AtendimentoModel next;
}