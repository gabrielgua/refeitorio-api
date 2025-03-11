package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.ClientMapper;
import com.gabrielgua.refeitorio.api.model.ClientResponse;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.service.FindClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final FindClientService findClientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public List<ClientResponse> findAll(@Param("term") String term) {
        if (term == null || term.isEmpty()) {
            return clientMapper.toCollectionModel(findClientService.findAll());
        }
        return clientMapper.toCollectionModel(findClientService.searchForClient(term));
    }

    @GetMapping("/{credential}")
    private ClientResponse getByCredential(@PathVariable String credential) {
        return clientMapper.toModel(findClientService.findByCredential(credential));
    }
}