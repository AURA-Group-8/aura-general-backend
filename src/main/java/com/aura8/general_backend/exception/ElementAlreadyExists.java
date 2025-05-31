package com.aura8.general_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ElementAlreadyExists extends RuntimeException{
    public ElementAlreadyExists() {
    }

    public ElementAlreadyExists(String message) {
        super(message);
    }
}
