package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.BusinessException;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.model.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.repository.OrderDiscountStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDiscountStrategyService {

    private final OrderDiscountStrategyRepository repository;
    private final CredentialRangeService credentialRangeService;

    public Optional<OrderDiscountStrategy> findByClient(Client client) {
        var credentialRange = credentialRangeService.findByCredential(client.getCredential());
        var discountStrategies = findByCredentialRange(credentialRange);

        if (discountStrategies.size() > 1) {
            return discountStrategies.stream()
                    .filter(strategy -> strategy.salaryApplies(client.getSalary()))
                    .findFirst();
        }

        return discountStrategies.stream().findFirst();
    }

    public List<OrderDiscountStrategy> findByCredentialRange(CredentialRange range) {
        return repository.findByCredentialRange(range);
    }

    public List<OrderDiscountStrategy> findAll() {
        return repository.findAll();
    }




}