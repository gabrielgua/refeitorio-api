package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.ClientResponse;
import com.gabrielgua.refeitorio.api.model.ConsultClientResponse;
import com.gabrielgua.refeitorio.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final CredentialRangeMapper credentialRangeMapper;

    public ClientResponse toModel(Client client) {
        var cRange = credentialRangeMapper.toClientResponse(client.getCredentialRange());
        return ClientResponse.builder()
                .name(client.getName())
                .role(client.getRole())
                .balance(client.getBalance())
                .credential(client.getCredential())
                .freeOfCharge(client.getFreeOfCharge())
                .credentialRange(cRange)
                .build();
    }

    public List<ClientResponse> toCollectionModel(List<Client> clients) {
        return clients.stream()
                .map(this::toModel)
                .toList();
    }

    public Client toEntity(ConsultClientResponse consultClientResponse) {
        var client = new Client();
        client.setName(consultClientResponse.getName());
        client.setRole(consultClientResponse.getRole());
        client.setSalary(consultClientResponse.getSalary());
        client.setCredential(consultClientResponse.getCredential());
        return client;
    }
}