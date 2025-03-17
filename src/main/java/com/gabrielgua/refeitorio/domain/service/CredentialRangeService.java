package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.BusinessException;
import com.gabrielgua.refeitorio.domain.exception.CredentialRangeNotFound;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.repository.CredentialRangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialRangeService {

    private final CredentialRangeRepository credentialRangeRepository;

    @Transactional(readOnly = true)
    public List<CredentialRange> findAll() {
        return credentialRangeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CredentialRange findByCredential(String credential) {
        return credentialRangeRepository.findByCredential(Integer.parseInt(credential))
                .orElseThrow(() -> new CredentialRangeNotFound("CredentialRange not found for credential: " + credential));
    }
}