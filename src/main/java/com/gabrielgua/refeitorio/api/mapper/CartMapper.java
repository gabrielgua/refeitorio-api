package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.CartRequest;
import com.gabrielgua.refeitorio.api.model.CartResponse;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final OrderItemMapper orderItemMapper;
    public CartResponse toResponse(Order order, List<OrderItem> items, BigDecimal discount) {
        return CartResponse.builder()
                .items(orderItemMapper.toCollectionModel(items))
                .finalPrice(order.getFinalPrice())
                .originalPrice(order.getOriginalPrice())
                .discountedPrice(order.getDiscountedPrice())
                .discount(discount)
                .build();
    }
}