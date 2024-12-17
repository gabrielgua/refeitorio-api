package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.api.exception.AtendimentoNotFound;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository repository;


    @Transactional(readOnly = true)
    public List<Atendimento> listAll() {
        return repository.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<Atendimento> findCurrentAtendimento() {
        var currentTime = LocalTime.now();
        return repository.findCurrent(currentTime);
    }

    @Transactional(readOnly = true)
    public Atendimento findNextAtendimento() {
        var currentTime = LocalTime.now();
        return repository
                .findFirstByTimeStartAfterOrderByTimeStartAsc(currentTime)
                .or(() -> repository.findFirstByTimeStartBeforeOrderByTimeStartAsc(currentTime))
                .orElseThrow(() -> new AtendimentoNotFound("Could not find next Atendimento"));
    }
    @Transactional(readOnly = true)
    public Atendimento findPreviousAtendimento() {
        var currentTime = LocalTime.now();
        return repository.findFirstByTimeEndBeforeOrderByTimeEndDesc(currentTime)
                .or(() -> repository.findFirstByTimeEndAfterOrderByTimeEndDesc(currentTime))
                .orElseThrow(() -> new AtendimentoNotFound("Could not find previous Atendimento"));
    }

}