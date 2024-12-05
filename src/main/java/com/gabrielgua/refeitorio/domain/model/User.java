package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Data
@Entity
public class User {

    @Id
    @EqualsAndHashCode.Include
    @JdbcTypeCode(Types.VARCHAR)
    private UUID credential;

    private String name;
    private String surname;
    private BigDecimal salary;
}