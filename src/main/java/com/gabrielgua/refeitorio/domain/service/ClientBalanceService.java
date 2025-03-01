package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ClientBalanceService {

    private final ClientRepository clientRepository;

    @Transactional
    public void deposit(Client client, BigDecimal amount) {
        client.setBalance(client.getBalance().add(amount));
        clientRepository.save(client);
    }

    @Transactional
    public void withdraw(Client client, BigDecimal amount) {
        client.setBalance(client.getBalance().subtract(amount));
        clientRepository.save(client);
    }
}