package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Embeddable
public class AtendimentoProductId {

    @JoinColumn(name = "product_id")
    private Long productId;

    @JoinColumn(name = "atendimento_id")
    private Long atendimentoId;
}