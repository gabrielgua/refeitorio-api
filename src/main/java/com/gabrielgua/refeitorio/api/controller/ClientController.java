package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.ClientMapper;
import com.gabrielgua.refeitorio.api.model.ClientResponse;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.service.FindClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final FindClientService findClientService;
    private final ClientMapper clientMapper;

    @GetMapping("/{credential}")
    private ClientResponse getByCredential(@PathVariable String credential) {
        return clientMapper.toModel(findClientService.findByCredential(credential));
    }
}