package com.gabrielgua.refeitorio.api.security;

import com.gabrielgua.refeitorio.domain.model.Usuario;
import com.gabrielgua.refeitorio.domain.service.UsuarioService;
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
    private final UsuarioService userService;

    public AuthResponse register(Usuario user) {
        var token = tokenService.generateToken(user, defaultClaims(user));

        return AuthResponse.builder()
                .credential(user.getCredential())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userService.findByEmail(request.getEmail());
        var token = tokenService.generateToken(user, defaultClaims(user));

        return AuthResponse.builder()
                .credential(user.getCredential())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    private Map<String, Object> defaultClaims(Usuario user) {
        var claims = new HashMap<String, Object>();

        claims.put("credential", user.getCredential());

        return claims;
    }

}