package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.BalanceMovementMapper;
import com.gabrielgua.refeitorio.api.mapper.PageableMapper;
import com.gabrielgua.refeitorio.api.model.BalanceMovementRequest;
import com.gabrielgua.refeitorio.api.model.BalanceMovementResponse;
import com.gabrielgua.refeitorio.api.model.PagedResponse;
import com.gabrielgua.refeitorio.domain.filter.BalanceMovementFilter;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import com.gabrielgua.refeitorio.domain.service.BalanceMovementService;
import com.gabrielgua.refeitorio.domain.service.ClientBalanceService;
import com.gabrielgua.refeitorio.domain.service.ClientService;
import com.gabrielgua.refeitorio.infra.spec.BalanceMovementSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients/{credential}/balance-movements")
public class BalanceMovementController {

    private final BalanceMovementService service;
    private final BalanceMovementMapper mapper;
    private final ClientService clientService;
    private final ClientBalanceService clientBalanceService;
    private final PageableMapper pageableMapper;


    @GetMapping
    public PagedResponse<BalanceMovementResponse> listByCredential(@PathVariable String credential, @Valid BalanceMovementFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        var client = clientService.findByCredential(credential);

        Sort.Direction direction;
        //handles invalid sort field and defaults it to DESC
        try {
            direction = Sort.Direction.fromString(filter.getSort());
        } catch (IllegalArgumentException | NullPointerException e) {
            direction = Sort.Direction.DESC;
        }

        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, "timestamp"));
        Page<BalanceMovement> page = service.findByClientCredential(client.getCredential(), sorted, BalanceMovementSpecs.filter(filter));
        return pageableMapper.toModel(page, mapper::toResponse);
    }

    @PostMapping
    public BalanceMovementResponse adjustBalance(@PathVariable String credential, @Valid @RequestBody BalanceMovementRequest request) {
        var client = clientService.findByCredential(credential);
        return mapper.toResponse(clientBalanceService.adjust(client, request.getAmount()));
    }
}