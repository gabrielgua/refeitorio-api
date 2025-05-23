package com.gabrielgua.refeitorio.api.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}