package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    public Boolean applies(String credential) {
        var credentialInteger = Integer.parseInt(credential);
        return credentialInteger >= min && credentialInteger <= max;
    }
}