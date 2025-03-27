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

        var store = new Store();
        store.setId(request.getStoreId());

        var order = new Order();
        order.setStore(store);
        order.setClient(client);
        order.setAtendimento(atendimento);
        order.setItems(orderItemMapper.toOrderItemEntityCollection(request.getItems()));
        return order;
    }

    public OrderResponse toModelCreated(Order order) {
        return OrderResponse.builder()
                .number(order.getNumber())
                .build();
    }

    public OrderResponse toModel(Order order) {
        var client = ClientResponse.builder()
                .credential(order.getClient().getCredential())
                .name(order.getClient().getName())
                .build();

        var atendimento = AtendimentoResponse.builder()
                .id(order.getAtendimento().getId())
                .name(order.getAtendimento().getName())
                .build();

        return OrderResponse.builder()
                .id(order.getId())
                .number(order.getNumber())
                .finalPrice(order.getFinalPrice())
                .originalPrice(order.getOriginalPrice())
                .discountedPrice(order.getDiscountedPrice())
                .items(orderItemMapper.toCollectionModel(order.getItems()))
                .client(client)
                .atendimento(atendimento)
                .createdAt(order.getCreatedAt())
                .build();
    }

    public List<OrderResponse> toCollectionModel(List<Order> orders) {
        return orders.stream()
                .map(this::toModel)
                .toList();
    }








}