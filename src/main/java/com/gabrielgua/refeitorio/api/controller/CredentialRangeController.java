package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.CredentialRangeMapper;
import com.gabrielgua.refeitorio.api.mapper.PageableMapper;
import com.gabrielgua.refeitorio.api.model.CredentialRangeResponse;
import com.gabrielgua.refeitorio.api.model.PagedResponse;
import com.gabrielgua.refeitorio.domain.filter.CredentialRangeFilter;
import com.gabrielgua.refeitorio.domain.service.CredentialRangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credential-ranges")
public class CredentialRangeController {

    private final CredentialRangeService service;
    private final CredentialRangeMapper mapper;
    private final PageableMapper pageableMapper;

    @GetMapping
    public PagedResponse<CredentialRangeResponse> findAll(@PageableDefault(size = 10) Pageable pageable, CredentialRangeFilter filter) {
        var page = service.findAll(pageable, filter);
        return pageableMapper.toModel(page, mapper::toResponse);
    }
}