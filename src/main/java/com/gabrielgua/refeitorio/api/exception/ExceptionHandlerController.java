package com.gabrielgua.refeitorio.api.exception;

import com.gabrielgua.refeitorio.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private ExceptionService service;


    public ResponseEntity<?> handleExceptionInternal(Problem problem) {
        return ResponseEntity
                .status(problem.getStatus())
                .body(problem);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(service.createProblem(status.name(), ex.getMessage(), status.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(service.createProblem(status.name(), ex.getMessage(), status.value()));
    }

   



}