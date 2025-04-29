package com.aura8.general_backend.exception;

public class BadCredencialsException extends RuntimeException {
    public BadCredencialsException(String message) {
        super(message);
    }
}
