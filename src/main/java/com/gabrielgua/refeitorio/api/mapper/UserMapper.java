package com.gabrielgua.refeitorio.api.mapper;

import com.gabrielgua.refeitorio.api.model.StoreResponse;
import com.gabrielgua.refeitorio.api.model.UserResponse;
import com.gabrielgua.refeitorio.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        var store = StoreResponse.builder()
                .id(user.getStore().getId())
                .name(user.getStore().getName())
                .build();

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .store(store)
                .build();
    }
}