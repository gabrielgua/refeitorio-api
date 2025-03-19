package com.gabrielgua.refeitorio.api.security;

import com.gabrielgua.refeitorio.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthProperties properties;

    private String buildToken(Map<String, Object> extraClaims, String subject) {
        var now = System.currentTimeMillis();
        var expiration = now + properties.getToken().getExpiration();

        return Jwts.builder()
                .subject(subject)
                .claims(extraClaims)
                .issuer("refeitorio-api")
                .issuedAt(new Date(now))
                .expiration(new Date(expiration))
                .signWith(secretKey())
                .compact();
    }

    public String generateToken(User user) {
        return buildToken(new HashMap<>(), user.getEmail());
    }

    public String generateToken(User user, Map<String, Object> extraClaims) {
        return buildToken(extraClaims, user.getEmail());
    }

    public boolean isTokenValid(String token, String subject) {
        try {
            final String email = getTokenSubject(token);
            return email.equals(subject) && getTokenExpiration(token).after(new Date());
        } catch (SignatureException e) {
            return false;
        }
    }

    public String getTokenSubject(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Date getTokenExpiration(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(properties.getToken().getSecret().getBytes());
    }

}