package com.gabrielgua.refeitorio.api.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@Getter
@Setter
@ConfigurationProperties("api.security")
public class AuthProperties {

    private AuthProperties.Token token;

    @Getter
    @Setter
    @Validated
    public static class Token {
        @NotBlank
        private String secret;

        @NotNull
        private long expiration;
    }
}