package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.BalanceMovementResponse;
import com.gabrielgua.refeitorio.domain.model.BalanceMovement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BalanceMovementMapper {

    public BalanceMovementResponse toResponse(BalanceMovement balanceMovement) {
        return BalanceMovementResponse.builder()
                .id(balanceMovement.getId())
                .credential(balanceMovement.getClient().getCredential())
                .amount(balanceMovement.getAmount())
                .oldBalance(balanceMovement.getOldBalance())
                .newBalance(balanceMovement.getNewBalance())
                .type(balanceMovement.getType())
                .timestamp(balanceMovement.getTimestamp())
                .build();
    }

    public List<BalanceMovementResponse> toCollectionResponse(List<BalanceMovement> balanceMovements) {
        return  balanceMovements.stream()
                .map(this::toResponse)
                .toList();
    }

}