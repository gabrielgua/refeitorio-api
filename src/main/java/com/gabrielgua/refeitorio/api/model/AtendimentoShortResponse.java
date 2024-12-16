package com.gabrielgua.refeitorio.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class AtendimentoShortResponse {

    private String name;
    private LocalTime timeStart;
    private LocalTime timeEnd;
}