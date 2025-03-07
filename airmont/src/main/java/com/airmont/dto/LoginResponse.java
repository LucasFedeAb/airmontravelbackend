package com.airmont.dto;

public class LoginResponse {
    private boolean success;
    private String token;
    private String message;
    private String email;
    private String username;

    public LoginResponse(boolean success, String token, String message, String email, String username) {
        this.success = success;
        this.token = token;
        this.message = message;
        this.email = email;
        this.username = username;
    }

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
