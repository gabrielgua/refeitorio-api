package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final ProductService productService;
    private final UserService userService;
    private final ClientService clientService;
    private final AtendimentoService atendimentoService;

    @Transactional
    public Order save(Order order) {
        validateOrder(order);
        validateItems(order);

        var discount = clientService.getDiscount(order.getClient());
        order.calculatePrice(discount);
        return repository.save(order);
    }

    public void validateItems(Order order) {
        order.getItems().forEach(item -> {
            var product = productService.findByCode(item.getProduct().getCode());

            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }

    public void validateOrder(Order order) {
        var client = clientService.findByCredential(order.getClient().getCredential());
        var atendimento = atendimentoService.findById(order.getAtendimento().getId());

        order.setClient(client);
        order.setAtendimento(atendimento);
    }
}