package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.CredentialRange;
import com.gabrielgua.refeitorio.domain.model.OrderDiscountStrategy;
import com.gabrielgua.refeitorio.domain.repository.OrderDiscountStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDiscountStrategyService {

    private final OrderDiscountStrategyRepository repository;
    private final CredentialRangeService credentialRangeService;

    public OrderDiscountStrategy findByClient(Client client) {
        var credentialRange = credentialRangeService.findByCredential(client.getCredential());
        var discountStrategies = findByCredentialRange(credentialRange);
        if (discountStrategies.isEmpty()) {
            throw new BusinessException("Couldn't find discount strategy for this client credential: " + client.getCredential());
        }

        if (discountStrategies.size() > 1) {
            return discountStrategies.stream()
                    .filter(strategy -> strategy.salaryApplies(client.getSalary()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("Couldn't find discount strategy for this client's salary"));
        }

        return discountStrategies.getFirst();
    }

    public List<OrderDiscountStrategy> findByCredentialRange(CredentialRange range) {
        return repository.findByCredentialRange(range);
    }

    public List<OrderDiscountStrategy> findAll() {
        return repository.findAll();
    }




}