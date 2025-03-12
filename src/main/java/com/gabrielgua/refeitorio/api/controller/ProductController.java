package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.ProductMapper;
import com.gabrielgua.refeitorio.api.model.ProductResponse;
import com.gabrielgua.refeitorio.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductResponse> findAll(@Param("term") String term) {
        if (term == null) {
            return productMapper.toCollectionResponse(productService.findAll());
        }
        return productMapper.toCollectionResponse(productService.findByTerm(term));
    }

    @GetMapping("/{productCode}")
    public ProductResponse getProduct(@PathVariable String productCode) {
        return productMapper.toResponse(productService.findByCode(productCode));
    }

}