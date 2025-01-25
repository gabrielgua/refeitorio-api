package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.*;
import com.gabrielgua.refeitorio.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public Order toEntity(OrderRequest request) {
        var atendimento = new Atendimento();
        atendimento.setId(request.getAtendimentoId());

        var client = new Client();
        client.setCredential(request.getCredential());

        var order = new Order();
        order.setClient(client);
        order.setAtendimento(atendimento);
        order.setItems(orderItemMapper.toOrderItemEntityCollection(request.getItems()));
        return order;
    }

    public OrderModel toModelCreated(Order order) {
        return OrderModel.builder()
                .number(order.getNumber())
                .build();
    }

    public OrderModel toModel(Order order) {
        var user = UserModel.builder()
                .credential(order.getClient().getCredential())
                .name(order.getClient().getName())
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