package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.api.exception.InvalidPaymentType;
import com.gabrielgua.refeitorio.domain.exception.ClientBalanceLimitReachedException;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.PaymentType;
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
    private final BalanceMovementService balanceMovementService;

    @Transactional
    public BalanceMovement adjust(Client client, BigDecimal amount) {
        validateClientBalance(client);

        var oldBalance = client.getBalance();
        var newBalance = oldBalance.add(amount);

        client.setBalance(client.getBalance().add(amount));
        clientRepository.save(client);

        return balanceMovementService.save(BalanceMovement.builder()
                .amount(amount)
                .client(client)
                .oldBalance(oldBalance)
                .newBalance(newBalance)
                .type(BalanceMovementType.ADJUSTMENT)
                .build());
    }

    @Transactional
    public void withdraw(Client client, BigDecimal amount) {
        validateClientBalance(client);
        validateBalanceLimit(client);  // validates the negative limit
        balanceMovementService.save(client, amount, BalanceMovementType.PURCHASE);
        client.setBalance(client.getBalance().subtract(amount));
        clientRepository.save(client);
    }

    public void validateBalanceLimit(Client client) {
        if (client.getBalance().compareTo(NEGATIVE_BALANCE_LIMIT) < 0) {
            throw new ClientBalanceLimitReachedException();
        }
    }

    public void validateClientBalance(Client client) {
        if (!client.getCredentialRange().getPaymentType().equals(PaymentType.BALANCE_DEBIT)) {
            throw new InvalidPaymentType();
        }
    }
}