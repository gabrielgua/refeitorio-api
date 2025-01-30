package com.gabrielgua.refeitorio.domain.service;



import java.math.BigDecimal;


import com.gabrielgua.refeitorio.api.strategy.factory.OrderDiscountFactory;
import com.gabrielgua.refeitorio.domain.exception.ResourceNotFoundException;
import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {



    private final ClientRepository repository;
    private final OrderDiscountFactory discountFactory;

    @Transactional(readOnly = true)
    public Client findByCredential(String credential) {
        return repository.findByCredential(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }

    @Transactional
    public Client save(Client client){
        return  repository.save(client);
    }
    
    @Transactional(readOnly = true)
    public BigDecimal getDiscount(Atendimento atendimento, Client client) {
       return discountFactory
               .getStrategy(client.getCredential())
               .getDiscount(atendimento, client);
    }
}