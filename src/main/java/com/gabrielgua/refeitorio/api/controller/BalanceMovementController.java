package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.BalanceMovementMapper;
import com.gabrielgua.refeitorio.api.mapper.PageableMapper;
import com.gabrielgua.refeitorio.api.model.BalanceMovementRequest;
import com.gabrielgua.refeitorio.api.model.BalanceMovementResponse;
import com.gabrielgua.refeitorio.api.model.PagedResponse;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import com.gabrielgua.refeitorio.domain.model.BalanceMovementType;
import com.gabrielgua.refeitorio.domain.service.BalanceMovementService;
import com.gabrielgua.refeitorio.domain.service.ClientBalanceService;
import com.gabrielgua.refeitorio.domain.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients/{credential}/balance")
public class BalanceMovementController {

    private final BalanceMovementService service;
    private final BalanceMovementMapper mapper;
    private final ClientService clientService;
    private final ClientBalanceService clientBalanceService;
    private final PageableMapper pageableMapper;


    @GetMapping
    public PagedResponse<BalanceMovementResponse> findByCredential(@PageableDefault(size = 10) Pageable pageable, @PathVariable String credential) {
        var client = clientService.findByCredential(credential);

        Page<BalanceMovement> page = service.findByClientCredential(credential, pageable);
        return pageableMapper.toModel(page, mapper::toResponse);
    }

    @PostMapping
    public BalanceMovementResponse adjustBalance(@PathVariable String credential, @Valid @RequestBody BalanceMovementRequest request) {
        var client = clientService.findByCredential(credential);
        return mapper.toResponse(clientBalanceService.adjust(client, request.getAmount()));
    }
}