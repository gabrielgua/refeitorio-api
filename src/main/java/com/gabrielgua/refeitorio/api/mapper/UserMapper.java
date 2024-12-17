package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.UserResponse;
import com.gabrielgua.refeitorio.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .credential(user.getCredential())
                .name(user.getName())
                .email(user.getEmail())
                .balance(user.getBalance())
                .salary(user.getSalary())
                .role(user.getRole())
                .build();
    }

    public UserResponse toAtendimentoResponse(User user) {
        return UserResponse.builder()
                .credential(user.getCredential())
                .balance(user.getBalance())
                .name(user.getName())
                .build();
    }
}