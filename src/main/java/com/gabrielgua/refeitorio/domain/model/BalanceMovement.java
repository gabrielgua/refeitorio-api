package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "balance_movements")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BalanceMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credential")
    private Client client;

    private BigDecimal amount;
    private BigDecimal oldBalance;
    private BigDecimal newBalance;

    @Enumerated(EnumType.STRING)
    private BalanceMovementType type;

    @CreationTimestamp
    private OffsetDateTime timestamp;



}