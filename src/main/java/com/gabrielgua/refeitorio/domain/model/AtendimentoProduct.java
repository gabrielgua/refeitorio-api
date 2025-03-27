package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atendimentos_products")
public class AtendimentoProduct {

    @EmbeddedId
    private AtendimentoProductId id;

    @ManyToOne
    @MapsId("atendimentoId")
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @JoinColumn(name = "is_mandatory")
    private Boolean isMandatory;
}