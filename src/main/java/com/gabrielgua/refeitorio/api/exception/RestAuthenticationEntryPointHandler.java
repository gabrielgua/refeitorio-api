package com.gabrielgua.refeitorio.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class RestAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var status = HttpStatus.UNAUTHORIZED;
        var problem = Problem.builder()
                .status(status.value())
                .error("UNAUTHORIZED")
                .message("Missing credentials or token invalid")
                .timestamp(OffsetDateTime.now())
                .build();

        response.setStatus(status.value());
        response.setContentType("application/json");

        var out = response.getOutputStream();

        new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writerWithDefaultPrettyPrinter()
                .writeValue(out, problem);

        out.flush();
    }
}