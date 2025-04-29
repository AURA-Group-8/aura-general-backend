package com.aura8.general_backend.exception;

public class InsufficienteAuthenticationException extends RuntimeException {
    public InsufficienteAuthenticationException(String message) {
        super(message);
    }
}
