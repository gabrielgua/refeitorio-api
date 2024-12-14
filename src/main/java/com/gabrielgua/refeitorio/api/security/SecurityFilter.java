package com.gabrielgua.refeitorio.api.security;

import com.gabrielgua.refeitorio.api.exception.OutputResponseHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final OutputResponseHelper helper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token;
        final String email;
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = header.substring(7);
        try {
            email = tokenService.getTokenSubject(token);

            if (email != null && !isAuthenticated()) {
                var userDetails = userDetailsService.loadUserByUsername(email);
                    if (tokenService.isTokenValid(token, userDetails.getUsername())) {
                        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                    filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException ex) {
            helper.tokenExpired(response);
        } catch (MalformedJwtException | SignatureException ex) {
            helper.tokenInvalid(response);
        }
    }

    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}