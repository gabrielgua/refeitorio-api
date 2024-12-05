package com.gabrielgua.refeitorio.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class Problem {

    private int status;
    private String error;
    private String message;
    private OffsetDateTime timestamp;
}