package com.aura8.general_backend.clean_arch.core.domain;

public class UsersToken {
    private String token;

    public UsersToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
