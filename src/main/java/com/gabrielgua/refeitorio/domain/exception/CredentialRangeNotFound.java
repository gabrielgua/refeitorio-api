package com.gabrielgua.refeitorio.domain.exception;

public class CredentialRangeNotFound extends RuntimeException {
  public CredentialRangeNotFound(String message) {
    super(message);
  }
}