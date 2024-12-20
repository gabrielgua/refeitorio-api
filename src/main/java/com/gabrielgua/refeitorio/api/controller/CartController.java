package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.CartMapper;
import com.gabrielgua.refeitorio.api.mapper.OrderItemMapper;
import com.gabrielgua.refeitorio.api.mapper.OrderMapper;
import com.gabrielgua.refeitorio.api.model.CartRequest;
import com.gabrielgua.refeitorio.api.model.CartResponse;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import com.gabrielgua.refeitorio.domain.service.OrderService;
import com.gabrielgua.refeitorio.domain.service.ProductService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final UserService userService;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;
    private final CartMapper cartMapper;

    @PostMapping("/calculate")
    public CartResponse calculatePrice(@Valid @RequestBody CartRequest request) {
        var user = userService.findByCredential(request.getCredential());
        var discount = BigDecimal.ZERO;

        var order = new Order();
        var items = orderItemMapper.toOrderItemEntityCollection(request.getItems());

        orderService.calculateTotalPrice(order, items, discount);
        return cartMapper.toResponse(order, items, discount);
    }
}