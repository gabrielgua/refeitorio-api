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

    public Product toEntity(ProductResponse model) {
        var product = new Product();
        product.setId(model.getId());
        product.setCode(model.getCode());
        product.setName(model.getName());
        product.setPrice(model.getPrice());
        product.setPriceType(model.getPriceType());
        return product;
    }
}