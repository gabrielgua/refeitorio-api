package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produtos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;
}