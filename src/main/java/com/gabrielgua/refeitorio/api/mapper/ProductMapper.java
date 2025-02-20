package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.ProductResponse;
import com.gabrielgua.refeitorio.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .price(product.getPrice())
                .allowMultiple(product.getAllowMultiple())
                .priceType(product.getPriceType())
                .build();
    }

    public ProductResponse toOrderItemResponse(Product product) {
        return ProductResponse.builder()
                .code(product.getCode())
                .name(product.getName())
                .allowMultiple(product.getAllowMultiple())
                .priceType(product.getPriceType())
                .build();
    }

    public Product toEntity(ProductResponse request) {
        var product = new Product();
        product.setId(request.getId());
        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setPriceType(request.getPriceType());
        return product;
    }
}