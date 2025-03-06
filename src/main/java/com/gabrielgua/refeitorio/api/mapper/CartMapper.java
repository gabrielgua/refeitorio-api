package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.CartRequest;
import com.gabrielgua.refeitorio.api.model.CartResponse;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final OrderItemMapper orderItemMapper;
    public CartResponse toResponse(Order order) {
        return CartResponse.builder()
                .items(orderItemMapper.toCollectionModel(order.getItems()))
                .finalPrice(order.getFinalPrice().setScale(2, RoundingMode.HALF_UP))
                .originalPrice(order.getOriginalPrice().setScale(2, RoundingMode.HALF_UP))
                .discountedPrice(order.getDiscountedPrice().setScale(2, RoundingMode.HALF_UP))
                .discount(getTotalDiscountPercentage(order))
                .build();
    }

    private BigDecimal getTotalDiscountPercentage(Order order) {
        var discount = order.getOriginalPrice().subtract(order.getFinalPrice());
        if  (discount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        var discountPercentage = discount.divide(order.getOriginalPrice(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        return discountPercentage.setScale(0, RoundingMode.HALF_UP);
    }
}