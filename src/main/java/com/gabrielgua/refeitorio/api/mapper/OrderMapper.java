package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.OrderItemModel;
import com.gabrielgua.refeitorio.api.model.OrderItemRequest;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import com.gabrielgua.refeitorio.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductMapper productMapper;
    private final ProductService productService;

    public OrderItemModel toOrderItemModel(OrderItem orderItem) {
        return OrderItemModel.builder()
                .id(orderItem.getId())
                .product(productMapper.toOrderItemResponse(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .unitPrice(orderItem.getUnitPrice())
                .build();
    }

    public List<OrderItemModel> toCollectionModel(List<OrderItem> items) {
        return items.stream()
                .map(this::toOrderItemModel)
                .toList();
    }

    public OrderItem toEntity(OrderItemRequest request) {
        var product = productService.findByCode(request.getProductCode());
        var orderItem = new OrderItem();

        orderItem.setProduct(product);
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setQuantity(request.getQuantity());
        return orderItem;
    }
}