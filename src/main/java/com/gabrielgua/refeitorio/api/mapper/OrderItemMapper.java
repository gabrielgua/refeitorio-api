package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.OrderItemResponse;
import com.gabrielgua.refeitorio.api.model.OrderItemRequest;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import com.gabrielgua.refeitorio.domain.model.Product;
import com.gabrielgua.refeitorio.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public OrderItem toOrderItemEntity(OrderItemRequest request) {
        var product = new Product();
        product.setCode(request.getProductCode());

        var orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setWeight(request.getWeight());
        return orderItem;
    }

    public List<OrderItemResponse> toCollectionModel(List<OrderItem> items) {
        return items.stream()
                .map(this::toOrderItemModel)
                .toList();
    }

    public List<OrderItem> toOrderItemEntityCollection(List<OrderItemRequest> items) {
        return items.stream()
                .map(this::toOrderItemEntity)
                .toList();
    }

    public OrderItemResponse toOrderItemModel(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .product(productMapper.toOrderItemResponse(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getSubtotal().setScale(2, RoundingMode.HALF_UP))
                .subtotal(orderItem.getSubtotal().setScale(2, RoundingMode.HALF_UP))
                .discount(orderItem.getDiscount().multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP))
                .discountedPrice(orderItem.getDiscountedPrice().setScale(2, RoundingMode.HALF_UP))
                .unitPrice(orderItem.getUnitPrice().setScale(2, RoundingMode.HALF_UP))
                .weight(orderItem.getWeight())
                .build();
    }
}