package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.repository.BalanceMovementRepository;
import lombok.RequiredArgsConstructor;
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
    public List<BalanceMovement> findByClientCredential(String credential) {
        return repository.findByClientCredential(credential);
    }

    @Transactional
    public BalanceMovement save(BalanceMovement balanceMovement) {
        return repository.save(balanceMovement);
    }
}