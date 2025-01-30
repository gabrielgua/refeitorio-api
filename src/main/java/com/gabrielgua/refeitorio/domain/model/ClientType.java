package com.gabrielgua.refeitorio.domain.model;

import lombok.Getter;

@Getter
public enum ClientType {

    FUNCIONARIO_CLT_E_APRENDIZ(1, 29999),
    FUNCIONARIO_SND_PRODUCAO(1, 29999),
    ESTAGIARIO(60000, 69999),
    ACADEMICO_CEPEP(60000, 69999),
    RESIDENTE_E_ESPECIALIZANDO(70000, 79999),
    CORPO_CLINICO(50000, 59999),
    RFCC(30000, 39999);

    private Integer min;
    private Integer max;

    ClientType(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public Boolean isCredentialEligible(Integer credential) {
        return credential >= this.min && credential <= this.max;
    }
}