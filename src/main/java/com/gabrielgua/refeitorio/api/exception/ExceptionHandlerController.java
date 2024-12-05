package com.gabrielgua.refeitorio.api.exception;

import com.gabrielgua.refeitorio.domain.exception.LunchStatusException;
import com.gabrielgua.refeitorio.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    public Problem createProblem(String error, String message, int status) {
        return Problem.builder()
                .status(status)
                .error(error)
                .message(message)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public ResponseEntity<?> handleExceptionInternal(Problem problem) {
        return ResponseEntity
                .status(problem.getStatus())
                .body(problem);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(createProblem(status.name(), ex.getMessage(), status.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(createProblem(status.name(), ex.getMessage(), status.value()));
    }

    @ExceptionHandler(LunchStatusException.class)
    public ResponseEntity<?> handleLunchStatus(LunchStatusException ex) {
        var status = HttpStatus.CONFLICT;
        var problem = createProblem("LUNCH_STATUS", ex.getMessage(), status.value());
        return handleExceptionInternal(problem);
    }



}