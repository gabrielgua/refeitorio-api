package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final OrderService orderService;
    private final AtendimentoService atendimentoService;
    private final BennerClientService bennerClientService;

    public void validateCartOrder(Order order) {
        var client = bennerClientService.findByCredential(order.getClient().getCredential());
        var  atendimento = atendimentoService.findById(order.getAtendimento().getId());

        order.setClient(client);
        order.setAtendimento(atendimento);
    }
}