package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.UserModel;
import com.gabrielgua.refeitorio.domain.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserMapper {

    public UserModel toResponse(User user) {
        return UserModel.builder()
                .credential(user.getCredential())
                .name(user.getName())
                .email(user.getEmail())
                .balance(user.getBalance())
                .salary(user.getSalary())
                .role(user.getRole())
                .build();
    }

    public UserModel toAtendimentoResponse(User user) {
        var balance = user.getBalance();
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        return UserModel.builder()
                .credential(user.getCredential())
                .balance(balance)
                .name(user.getName())
                .build();
    }
}