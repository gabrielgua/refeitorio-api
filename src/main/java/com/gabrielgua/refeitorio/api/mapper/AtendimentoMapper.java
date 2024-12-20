package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.AtendimentoModel;
import com.gabrielgua.refeitorio.api.model.AtendimentoShortResponse;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoMapper {


    public AtendimentoShortResponse toShortResponse(Atendimento atendimento) {
        return AtendimentoShortResponse.builder()
                .name(atendimento.getName())
                .timeStart(atendimento.getTimeStart())
                .timeEnd(atendimento.getTimeEnd())
                .build();
    }

    public AtendimentoModel toResponse(Atendimento atendimento) {
        return AtendimentoModel.builder()
                .id(atendimento.getId())
                .name(atendimento.getName())
                .type(atendimento.getType())
                .timeStart(atendimento.getTimeStart())
                .timeEnd(atendimento.getTimeEnd())
                .build();
    }


}