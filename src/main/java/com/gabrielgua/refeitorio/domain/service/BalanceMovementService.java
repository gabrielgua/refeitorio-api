package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.filter.BalanceMovementFilter;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.repository.BalanceMovementRepository;
import com.gabrielgua.refeitorio.infra.spec.BalanceMovementSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceMovementService {

    private final BalanceMovementRepository repository;

    @Transactional
    public BalanceMovement save(Client client, BigDecimal amount, BalanceMovementType type) {
        amount = type.equals(BalanceMovementType.ADJUSTMENT) ? amount : amount.negate();
        return save(BalanceMovement.builder()
                .client(client)
                .oldBalance(client.getBalance())
                .newBalance(client.getBalance().add(amount))
                .amount(amount)
                .type(type)
                .build());
    }

    @Transactional(readOnly = true)
    public Page<BalanceMovement> findByClientCredential(String credential, Pageable pageable, Specification<BalanceMovement> spec) {
        Specification<BalanceMovement> credentialSpec = (root, _, cb) ->
                cb.equal(root.get("client").get("credential"), credential);

        spec = spec == null ? credentialSpec : spec.and(credentialSpec);
        return repository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Page<BalanceMovement> findAll(Pageable pageable, BalanceMovementFilter filter) {
        return repository.findAll(BalanceMovementSpecs.filter(filter), pageable);
    }

    @Transactional
    public BalanceMovement save(BalanceMovement balanceMovement) {
        return repository.save(balanceMovement);
    }

}