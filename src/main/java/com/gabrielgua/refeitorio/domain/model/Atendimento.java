package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "atendimentos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Atendimento {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "atendimentos_products",
            joinColumns = @JoinColumn(name = "atendimento_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    private LocalTime timeStart;
    private LocalTime timeEnd;

    @Enumerated(EnumType.STRING)
    private AtendimentoType type;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}