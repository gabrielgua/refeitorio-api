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
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Lunch {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_credential", nullable = false)
    private User user;

    private BigDecimal weight;
    private BigDecimal price;

    @CreationTimestamp
    private OffsetDateTime created_at;

    @Enumerated(EnumType.STRING)
    private LunchStatus status;
}