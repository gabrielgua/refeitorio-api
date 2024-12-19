package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.OrderMapper;
import com.gabrielgua.refeitorio.api.model.CartRequest;
import com.gabrielgua.refeitorio.api.model.CartResponse;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import com.gabrielgua.refeitorio.domain.service.ProductService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderMapper mapper;

    @PostMapping("/calculate")
    public CartResponse calculatePrice(@Valid @RequestBody CartRequest request) {
        var user = userService.findByCredential(request.getCredential());
        var discount = BigDecimal.ZERO;

        var order = new Order();
        var items = request.getItems().stream()
                .map(mapper::toEntity)
                .toList();

        order.setItems(items);
        order.getItems().forEach(OrderItem::calculateTotalPrice);

        order.calculatePrice(discount);

        return CartResponse.builder()
                .items(mapper.toCollectionModel(items))
                .finalPrice(order.getFinalPrice())
                .originalPrice(order.getOriginalPrice())
                .discountedPrice(order.getDiscountedPrice())
                .discount(discount)
                .build();
    }
}