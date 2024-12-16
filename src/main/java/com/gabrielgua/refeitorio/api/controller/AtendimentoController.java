package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.AtendimentoMapper;
import com.gabrielgua.refeitorio.api.model.AtendimentoScheduleResponse;
import com.gabrielgua.refeitorio.domain.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service;
    private final AtendimentoMapper mapper;

    @GetMapping("/schedule")
    public AtendimentoScheduleResponse getSchedule() {
        var current = service.findCurrentAtendimento();
        var serving = current.isPresent();

        var next = service.findNextAtendimento();
        var previous = service.findPreviousAtendimento();

        var response = AtendimentoScheduleResponse.builder()
                .serving(serving)
                .next(mapper.toShortResponse(next))
                .previous(mapper.toShortResponse(previous));

        current.ifPresent(atendimento -> response.current(mapper.toResponse(atendimento)));

        return response.build();
    }
}