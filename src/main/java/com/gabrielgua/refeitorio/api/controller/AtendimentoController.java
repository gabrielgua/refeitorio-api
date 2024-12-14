package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.domain.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service;


}