package com.gabrielgua.refeitorio.api.security;

import com.gabrielgua.refeitorio.domain.model.User;
import com.gabrielgua.refeitorio.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UserService userService;

    private AuthResponse toModel (User user, String token) {
        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .store(user.getStore().getName())
                .token(token)
                .build();
    }

    public AuthResponse register(User user) {
        var token = tokenService.generateToken(user, defaultClaims(user));
        return toModel(user, token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userService.findByEmail(request.getEmail());
        var token = tokenService.generateToken(user, defaultClaims(user));
        return toModel(user, token);
    }

    private Map<String, Object> defaultClaims(User user) {
        var claims = new HashMap<String, Object>();
        claims.put("user_id", user.getId());
        return claims;
    }

}