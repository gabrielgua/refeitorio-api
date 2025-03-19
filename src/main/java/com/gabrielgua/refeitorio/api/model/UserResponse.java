package com.gabrielgua.refeitorio.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.refeitorio.domain.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String email;
    private String store;
    private UserRole role;
}