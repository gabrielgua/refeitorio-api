package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import com.gabrielgua.refeitorio.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    @Transactional
    public Order save(Order order) {
        order.getItems().forEach(item -> item.setOrder(order));

        return repository.save(order);
    }

    private List<OrderItem> calculateItemsPrice(List<OrderItem> items) {
        items.forEach(OrderItem::calculateTotalPrice);
        return items;
    }

    public void calculateTotalPrice(Order order, List<OrderItem> items, BigDecimal discount) {
        order.setItems(calculateItemsPrice(items));
        order.calculatePrice(discount);
    }
}