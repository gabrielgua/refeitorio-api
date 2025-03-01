package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.CartMapper;
import com.gabrielgua.refeitorio.api.mapper.OrderMapper;
import com.gabrielgua.refeitorio.api.model.CartResponse;
import com.gabrielgua.refeitorio.api.model.OrderRequest;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.service.ClientService;
import com.gabrielgua.refeitorio.domain.service.OrderService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final ClientService clientService;
    private final OrderService orderService;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    @PostMapping("/calculate")
    public CartResponse calculatePrice(@Valid @RequestBody OrderRequest request) {
        var order = orderMapper.toEntity(request);
        orderService.validateOrder(order);
        orderService.validateItems(order);

        orderService.calculateTotalPrice(order);

        return cartMapper.toResponse(order);
    }
}