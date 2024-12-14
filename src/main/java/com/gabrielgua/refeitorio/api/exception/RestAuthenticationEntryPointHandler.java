package com.gabrielgua.refeitorio.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final OutputResponseHelper helper;
    private final ExceptionService service;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var status = HttpStatus.UNAUTHORIZED;
        var problem = service.createProblem("UNAUTHORIZED", "Missing credentials or token invalid", status.value());

        System.out.println(authException.getMessage());
        helper.writeOutputResponse(response, problem);
    }
}