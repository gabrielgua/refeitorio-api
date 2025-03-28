package com.gabrielgua.refeitorio.api.security;

import com.gabrielgua.refeitorio.domain.model.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private Long id;
    private String token;
}