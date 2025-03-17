package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Order;
import com.gabrielgua.refeitorio.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final OrderService orderService;
    private final AtendimentoService atendimentoService;
    private final FetchClientService fetchClientService;

    public void validateCartOrder(Order order) {
        var client = fetchClientService.findByCredential(order.getClient().getCredential());
        var  atendimento = atendimentoService.findById(order.getAtendimento().getId());

        order.setClient(client);
        order.setAtendimento(atendimento);
    }
}