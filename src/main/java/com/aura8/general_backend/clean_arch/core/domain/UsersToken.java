package com.aura8.general_backend.clean_arch.core.domain;

public class UsersToken {
    private String token;
    private Integer userId;
    private String username;
    private String userEmail;

    public UsersToken(String token) {
        this.token = token;
    }

    public UsersToken(String token, Integer userId, String username, String userEmail) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
