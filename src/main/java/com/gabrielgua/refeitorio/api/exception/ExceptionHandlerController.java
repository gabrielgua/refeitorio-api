package com.gabrielgua.refeitorio.api.exception;

import com.gabrielgua.refeitorio.domain.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private final ExceptionService service;


    public ResponseEntity<?> handleExceptionInternal(Problem problem) {
        return ResponseEntity
                .status(problem.getStatus())
                .body(problem);
    }

    public ResponseEntity<?> handleNotFound(String error, String message) {
        var status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(service.createProblem(error, message, status.value()));
    }

    public ResponseEntity<?> handleConflict(String error, String message) {
        var status = HttpStatus.CONFLICT;
        return handleExceptionInternal(service.createProblem(error, message, status.value()));
    }

    public ResponseEntity<?> handleUnprocessableEntity(String error, String message) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return handleExceptionInternal(service.createProblem(error, message, status.value()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
        var status = HttpStatus.UNAUTHORIZED;
        return handleExceptionInternal(service.createProblem("TOKEN_EXPIRED", ex.getMessage(), status.value()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(service.createProblem(status.name(), ex.getMessage(), status.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return handleNotFound(status.name(), ex.getMessage());
    }

    @ExceptionHandler(CredentialRangeNotFound.class)
    public ResponseEntity<?> handleCredentialRangeNotFound(CredentialRangeNotFound ex) {
        return handleNotFound("CREDENTIAL_RANGE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return handleNotFound("USER_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(AtendimentoNotFoundException.class)
    public ResponseEntity<?> handleAtendimentoNotFound(AtendimentoNotFoundException ex) {
        return handleNotFound("ATENDIMENTO_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<?> handleStoreNotFound(StoreNotFoundException ex) {
        return handleNotFound("STORE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(ClientBalanceLimitReachedException.class)
    public ResponseEntity<?> handleClientBalanceLimitReached(ClientBalanceLimitReachedException ex) {
        return handleUnprocessableEntity("CLIENT_BALANCE_LIMIT_REACHED", ex.getMessage());
    }

    @ExceptionHandler(InvalidPaymentType.class)
    public ResponseEntity<?> handleInvalidPaymentType(InvalidPaymentType ex) {
        return handleUnprocessableEntity("INVALID_PAYMENT_TYPE", ex.getMessage());
    }

    @ExceptionHandler(CredentialRangeOverlapException.class)
    public ResponseEntity<?> handleCredentialRangeOverlap(CredentialRangeOverlapException ex) {
        return handleUnprocessableEntity("CREDENTIAL_RANGE_OVERLAP", ex.getMessage());
    }

    @ExceptionHandler(CredentialRangeInUseException.class)
    public ResponseEntity<?> handleCredentialRangeInUse(CredentialRangeInUseException ex) {
        return handleConflict("CREDENTIAL_RANGE_IN_USE", ex.getMessage());
    }
}