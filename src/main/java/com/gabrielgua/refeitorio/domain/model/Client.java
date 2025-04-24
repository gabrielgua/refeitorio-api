package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @EqualsAndHashCode.Include
    private String credential;
    private String name;
    private String role;
    private BigDecimal salary;
    private BigDecimal balance;
    private Boolean freeOfCharge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credential_range_id")
    private CredentialRange credentialRange;
}