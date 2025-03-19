package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.UserResponse;
import com.gabrielgua.refeitorio.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .store(user.getStore().getName())
                .role(user.getRole())
                .build();
    }
}