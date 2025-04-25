package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.CredentialRangeNotFound;
import com.gabrielgua.refeitorio.domain.filter.CredentialRangeFilter;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.repository.CredentialRangeRepository;
import com.gabrielgua.refeitorio.infra.spec.CredentialRangeSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CredentialRangeService {

    private final CredentialRangeRepository credentialRangeRepository;

    @Transactional(readOnly = true)
    public Page<CredentialRange> findAll(Pageable pageable, CredentialRangeFilter filter) {
        return credentialRangeRepository.findAll(CredentialRangeSpecs.filter(filter), pageable);
    }

    @Transactional(readOnly = true)
    public CredentialRange findByCredential(String credential) {
        return credentialRangeRepository.findByCredential(Integer.parseInt(credential))
                .orElseThrow(() -> new CredentialRangeNotFound("CredentialRange not found for credential: " + credential));
    }
}