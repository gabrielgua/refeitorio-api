package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.api.exception.BusinessException;
import com.gabrielgua.refeitorio.api.exception.ClientBalanceLimitReachedException;
import com.gabrielgua.refeitorio.domain.model.Client;
import com.gabrielgua.refeitorio.domain.model.Order;
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
    private final ProductService productService;
    private final ClientService clientService;
    private final FindClientService findClientService;
    private final AtendimentoService atendimentoService;
    private final ClientBalanceService clientBalanceService;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Order save(Order order) {
        validateOrder(order);
        validateItems(order);

        var discount = clientService.getDiscount(order.getAtendimento(), order.getClient());
        order.calculatePrice(discount);

        validateClientBalance(order);
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
        var client = findClientService.findByCredential(order.getClient().getCredential());
        var atendimento = atendimentoService.findById(order.getAtendimento().getId());

        order.setClient(client);
        order.setAtendimento(atendimento);
    }

    public void validateClientBalance(Order order) {
        var client = findClientService.findByCredential(order.getClient().getCredential());
        if (client.getBalance() == null) { // if the client doesn't have to use balance for orders
            return;
        }

        var balanceNew = client.getBalance().subtract(order.getFinalPrice());
        if (balanceNew.compareTo(BigDecimal.valueOf(-100)) < 0) {
            throw new ClientBalanceLimitReachedException();
        }

        clientBalanceService.withdraw(client, order.getFinalPrice());
    }
}