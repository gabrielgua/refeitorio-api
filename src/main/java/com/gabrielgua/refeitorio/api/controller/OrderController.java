package com.gabrielgua.refeitorio.api.controller;

import com.gabrielgua.refeitorio.api.mapper.OrderMapper;
import com.gabrielgua.refeitorio.api.model.OrderResponse;
import com.gabrielgua.refeitorio.api.model.OrderRequest;
import com.gabrielgua.refeitorio.domain.filter.OrderFilter;
import com.gabrielgua.refeitorio.domain.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderResponse> getOrders(OrderFilter filter) {
        return orderMapper.toCollectionModel(orderService.findAll(filter));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        var order = orderMapper.toEntity(request);
        return orderMapper.toModelCreated(orderService.save(order));
    }
}