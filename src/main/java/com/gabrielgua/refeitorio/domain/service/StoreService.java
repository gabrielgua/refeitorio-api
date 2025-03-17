package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.StoreNotFoundException;
import com.gabrielgua.refeitorio.domain.model.Store;
import com.gabrielgua.refeitorio.domain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Store findById(Long id) {
        return storeRepository.findById(id).orElseThrow(() -> new StoreNotFoundException(id));
    }
}