package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.ProductMapper;
import com.gabrielgua.refeitorio.api.model.ProductResponse;
import com.gabrielgua.refeitorio.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{productCode}")
    public ProductResponse getProduct(@PathVariable String productCode) {
        return productMapper.toResponse(productService.findByCode(productCode));
    }
}