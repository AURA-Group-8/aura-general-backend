package com.aura8.general_backend.exception;

public class UnauthorizedUserException extends RuntimeException {
  public UnauthorizedUserException(String message) {
    super(message);
  }
}
