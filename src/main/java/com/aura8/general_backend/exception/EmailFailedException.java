package com.aura8.general_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class EmailFailedException extends RuntimeException{
    public EmailFailedException(String message) {
        super(message);
    }
}
