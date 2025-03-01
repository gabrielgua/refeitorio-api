package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.ClientBalanceLimitReachedException;
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
    private final ProductService productService;
    private final ClientService clientService;
    private final FindClientService findClientService;
    private final AtendimentoService atendimentoService;
    private final ClientBalanceService clientBalanceService;
    private final OrderDiscountStrategyService discountStrategyService;
    private final OrderDiscountRuleService discountRuleService;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return repository.findAll();
    }


    @Transactional
    public Order save(Order order) {
        validateOrder(order);
        validateItems(order);
        calculateTotalPrice(order);
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

    public void calculateTotalPrice(Order order) {
        var client = clientService.findByCredential(order.getClient().getCredential());
        var discountStrategy = discountStrategyService.findByClient(client);

        var orderItems = order.getItems();
        //finds the rule for calculating the price or calculates it without a rule - 0% discount
        orderItems.forEach(item -> {
            discountRuleService.findByDiscountStrategyAndProduct(discountStrategy, item.getProduct())
                    .ifPresentOrElse(item::calculateTotalPrice, item::calculateTotalPrice);
        });

        var subtotal = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var discountedPrice = orderItems.stream()
                .map(OrderItem::getDiscountedPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOriginalPrice(subtotal);
        order.setDiscountedPrice(discountedPrice);
        order.setFinalPrice(subtotal.subtract(discountedPrice));
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