package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "atendimento", cascade =  CascadeType.ALL,  orphanRemoval = true)
    private List<AtendimentoProduct> productRelations = new ArrayList<>();

    private LocalTime timeStart;
    private LocalTime timeEnd;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}