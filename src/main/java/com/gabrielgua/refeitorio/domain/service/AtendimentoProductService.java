package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.model.AtendimentoProduct;
import com.gabrielgua.refeitorio.domain.model.AtendimentoProductId;
import com.gabrielgua.refeitorio.domain.model.Product;
import com.gabrielgua.refeitorio.domain.repository.AtendimentoProductRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtendimentoProductService {

    private final AtendimentoService atendimentoService;
    private final AtendimentoProductRespository repository;

    @Transactional(readOnly = true)
    public List<Product> getMandatoryProducts(Long atendimentoId) {
        var atendimento = atendimentoService.findById(atendimentoId);
        return repository.findByAtendimentoIdAndIsMandatoryTrue(atendimento.getId())
                .stream().map(AtendimentoProduct::getProduct)
                .toList();
    }
}