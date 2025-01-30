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
    private BigDecimal salary;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private ClientType type;
}