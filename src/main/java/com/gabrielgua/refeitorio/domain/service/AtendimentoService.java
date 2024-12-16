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

    private static final LocalTime currentTIME = LocalTime.now();

    @Transactional(readOnly = true)
    public List<Atendimento> listAll() {
        return repository.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<Atendimento> findCurrentAtendimento() {
        return repository.findCurrent(currentTIME);
    }

    @Transactional(readOnly = true)
    public Atendimento findNextAtendimento() {
        return repository
                .findFirstByTimeStartAfterOrderByTimeStartAsc(currentTIME)
                .or(() -> repository.findFirstByTimeStartBeforeOrderByTimeStartAsc(currentTIME))
                .orElseThrow(() -> new AtendimentoNotFound("Could not find next Atendimento"));
    }
    @Transactional(readOnly = true)
    public Atendimento findPreviousAtendimento() {
        return repository.findFirstByTimeEndBeforeOrderByTimeEndDesc(currentTIME)
                .or(() -> repository.findFirstByTimeEndAfterOrderByTimeEndDesc(currentTIME))
                .orElseThrow(() -> new AtendimentoNotFound("Could not find previous Atendimento"));
    }

}