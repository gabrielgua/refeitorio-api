package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.UserResponse;
import com.gabrielgua.refeitorio.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /*public UserModel toAtendimentoResponse(User user) {
        var balance = user.getBalance();
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        return UserModel.builder()
                .credential(user.getCredential())
                .balance(balance)
                .name(user.getName())
                .build();
    }*/
}