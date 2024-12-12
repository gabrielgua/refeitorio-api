package com.gabrielgua.refeitorio.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var status = HttpStatus.FORBIDDEN;
        var problem = Problem.builder()
                .status(status.value())
                .error("ACCESS_DENIED")
                .message("You don't have access to this resource.")
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