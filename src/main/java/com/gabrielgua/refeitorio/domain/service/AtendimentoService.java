package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository repository;
    @Transactional(readOnly = true)
    public List<Atendimento> listAll() {
        return repository.findAll();
    }


}