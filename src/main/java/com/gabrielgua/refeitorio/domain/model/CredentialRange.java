package com.gabrielgua.refeitorio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CredentialRange {

    FUNCIONARIOS(1, 29999),
    ESTAGIARIOS_E_ACADEMICOS(60000, 69999),
    RESIDENTES(70000, 79999),
    CORPO_CLINICO(50000, 59999),
    RFCC(30000, 39999),
    TERCEIROS(40000, 49999),
    TERCEIRIZADO(90000, 92999);

    private final Integer min;
    private final Integer max;

    public Boolean applies(Integer credential) {
        return credential >= this.min && credential <= this.max;
    }

}