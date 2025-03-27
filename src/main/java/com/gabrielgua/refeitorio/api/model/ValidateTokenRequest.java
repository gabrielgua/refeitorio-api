package com.gabrielgua.refeitorio.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {

    @NotNull
    private Long userId;

    @NotNull
    private String token;
}