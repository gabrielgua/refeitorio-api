package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.ClientResponse;
import com.gabrielgua.refeitorio.api.model.ConsultClientResponse;
import com.gabrielgua.refeitorio.domain.model.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientMapper {

    public ClientResponse toModel(Client client) {
        return ClientResponse.builder()
                .name(client.getName())
                .role(client.getRole())
                .salary(client.getSalary())
                .balance(client.getBalance())
                .credential(client.getCredential())
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