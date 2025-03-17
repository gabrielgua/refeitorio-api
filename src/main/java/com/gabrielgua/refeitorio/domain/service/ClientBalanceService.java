package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.ClientBalanceLimitReachedException;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ClientBalanceService {

    private static final BigDecimal NEGATIVE_BALANCE_LIMIT = BigDecimal.valueOf(-100);
    private final ClientRepository clientRepository;

    @Transactional
    public void deposit(Client client, BigDecimal amount) {
        client.setBalance(client.getBalance().add(amount));
        clientRepository.save(client);
    }

    @Transactional
    public void withdraw(Client client, BigDecimal amount) {
        validateBalanceLimit(client, amount);
        client.setBalance(client.getBalance().subtract(amount));
        clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public void validateBalanceLimit(Client client, BigDecimal amount) {
        if (client.getBalance() == null) { // if the client doesn't have to use balance for orders
            return;
        }

        var balanceNew = client.getBalance().subtract(amount);
        if (balanceNew.compareTo(NEGATIVE_BALANCE_LIMIT) < 0) {
            throw new ClientBalanceLimitReachedException();
        }
    }
}