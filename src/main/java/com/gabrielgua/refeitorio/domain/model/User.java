package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    private Long Id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}