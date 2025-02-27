package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "credential_ranges")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CredentialRange {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer min;
    private Integer max;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}