package com.gabrielgua.refeitorio.api.strategy;

import com.gabrielgua.refeitorio.domain.model.Atendimento;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoCodeValidator {

    public static final String CAFE_DA_MANHA = "CAFE_001";
    public static final String ALMOCO = "ALMOCO_002";
    public static final String LANCHE_DA_TARDE = "LANCHE_003";
    public static final String JANTAR = "JANTAR_004";


    public Boolean validate(Atendimento atendimento, String code) {
        return atendimento.getCode().equals(code);
    }
}