package com.gabrielgua.refeitorio.domain.service;



import java.math.BigDecimal;
import java.util.List;


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
    private final CredentialRangeService credentialRangeService;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public List<Client> searchForClient(String term) {
        return repository.findByNameContainingOrCredentialContaining(term, term);
    }

    @Transactional(readOnly = true)
    public Client findByCredential(String credential) {
        return repository.findByCredential(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }

    @Transactional
    public Client save(Client client){
        if (client.getFreeOfCharge() == null) {
            client.setFreeOfCharge(false);
        }

        if (client.getCredentialRange() == null) {
            var credentialRange = credentialRangeService.findByCredential(client.getCredential());
            client.setCredentialRange(credentialRange);
        }

        return repository.save(client);
    }
}