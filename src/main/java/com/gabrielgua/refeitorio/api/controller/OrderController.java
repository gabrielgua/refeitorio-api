package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.OrderItemMapper;
import com.gabrielgua.refeitorio.api.mapper.OrderMapper;
import com.gabrielgua.refeitorio.api.model.OrderModel;
import com.gabrielgua.refeitorio.api.model.OrderRequest;
import com.gabrielgua.refeitorio.domain.service.AtendimentoService;
import com.gabrielgua.refeitorio.domain.service.OrderService;
import com.gabrielgua.refeitorio.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final AtendimentoService atendimentoService;
    private final OrderMapper mapper;
    private final OrderItemMapper orderItemMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel createOrder(@Valid @RequestBody OrderRequest request) {
        var user = userService.findByCredential(request.getCredential());
        var discount = BigDecimal.ZERO;

        var atendimento = atendimentoService.findById(request.getAtendimentoId());

        var items = orderItemMapper.toOrderItemEntityCollection(request.getItems());
        var order = mapper.toEntity(user, atendimento, items);

        orderService.calculateTotalPrice(order, items, discount);
        return mapper.toModelCreated(orderService.save(order));
    }
}