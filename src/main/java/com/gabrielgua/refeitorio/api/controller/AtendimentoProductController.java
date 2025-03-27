package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.ProductMapper;
import com.gabrielgua.refeitorio.api.model.ProductResponse;
import com.gabrielgua.refeitorio.domain.service.AtendimentoProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/atendimentos/{atendimentoId}/products")
public class AtendimentoProductController {

    private final AtendimentoProductService service;
    private final ProductMapper productMapper;

    @GetMapping("/mandatory")
    public List<ProductResponse> getMandatoryProducts(@PathVariable Long atendimentoId) {
        return productMapper.toCollectionResponse(service.getMandatoryProducts(atendimentoId));
    }
}