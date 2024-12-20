package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.*;
import com.gabrielgua.refeitorio.domain.model.Atendimento;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.OrderItem;
import com.gabrielgua.refeitorio.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public Order toEntity(User user, Atendimento atendimento, List<OrderItem> items) {
        var order = new Order();
        order.setUser(user);
        order.setAtendimento(atendimento);
        order.setItems(items);
        return order;
    }

    public OrderModel toModelCreated(Order order) {
        return OrderModel.builder()
                .number(order.getNumber())
                .build();
    }

    public OrderModel toModel(Order order) {
        var user = UserModel.builder()
                .credential(order.getUser().getCredential())
                .name(order.getUser().getName())
                .build();

        var atendimento = AtendimentoModel.builder()
                .id(order.getAtendimento().getId())
                .name(order.getAtendimento().getName())
                .build();

        return OrderModel.builder()
                .id(order.getId())
                .number(order.getNumber())
                .finalPrice(order.getFinalPrice())
                .originalPrice(order.getOriginalPrice())
                .discountedPrice(order.getDiscountedPrice())
                .items(orderItemMapper.toCollectionModel(order.getItems()))
                .user(user)
                .atendimento(atendimento)
                .createdAt(order.getCreatedAt())
                .build();
    }

    public List<OrderModel> toCollectionModel(List<Order> orders) {
        return orders.stream()
                .map(this::toModel)
                .toList();
    }








}