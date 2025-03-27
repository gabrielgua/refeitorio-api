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

    public Boolean useBalance() {
        return this.balance != null;
    }
}