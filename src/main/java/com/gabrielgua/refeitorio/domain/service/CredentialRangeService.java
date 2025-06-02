package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.BusinessException;
import com.gabrielgua.refeitorio.domain.exception.CredentialRangeInUseException;
import com.gabrielgua.refeitorio.domain.exception.CredentialRangeNotFound;
import com.gabrielgua.refeitorio.domain.exception.CredentialRangeOverlapException;
import com.gabrielgua.refeitorio.domain.filter.CredentialRangeFilter;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.repository.ClientRepository;
import com.gabrielgua.refeitorio.domain.repository.CredentialRangeRepository;
import com.gabrielgua.refeitorio.infra.spec.CredentialRangeSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialRangeService {

    private final CredentialRangeRepository credentialRangeRepository;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<CredentialRange> findAll(Pageable pageable, CredentialRangeFilter filter) {
        return credentialRangeRepository.findAll(CredentialRangeSpecs.filter(filter), pageable);
    }

    @Transactional
    public CredentialRange save(CredentialRange credentialRange) {
        validateCredentialRange(credentialRange);
        return credentialRangeRepository.save(credentialRange);
    }

    @Transactional(readOnly = true)
    public CredentialRange findByCredential(String credential) {
        return credentialRangeRepository.findByCredential(Integer.parseInt(credential))
                .orElseThrow(() -> new CredentialRangeNotFound("CredentialRange not found for credential: " + credential));
    }

    @Transactional(readOnly = true)
    public List<CredentialRange> findOverlaps(Integer min, Integer max, Long id) {
        return credentialRangeRepository.findOverlappingRanges(min, max, id);
    }

    @Transactional(readOnly = true)
    public CredentialRange findById(Long id) {
        return credentialRangeRepository.findById(id).orElseThrow(() -> new CredentialRangeNotFound("CredentialRange not found for id: " + id));
    }

    private void validateCredentialRange(CredentialRange range) {
        if (range.getMin() > range.getMax()) {
            throw new BusinessException("Minimum credential cannot be greater than maximum.");
        }

        var overlaps = findOverlaps(range.getMin(), range.getMax(), range.getId());
        if (!overlaps.isEmpty()) {
            throw new CredentialRangeOverlapException();
        }
    }

    @Transactional
    public void remove(CredentialRange range) {
        var clients = clientRepository.findAllByCredentialRangeId(range.getId());

        if (!clients.isEmpty()) {
            throw new CredentialRangeInUseException();
        }

        credentialRangeRepository.delete(range);
    }
}