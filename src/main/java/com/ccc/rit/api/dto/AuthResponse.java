package com.ccc.rit.api.dto;

public class AuthResponse {
    private boolean success;
    private String message;
    private String token;
    private String expiresIn;

    public AuthResponse(boolean success, String message, String token, String expiresIn) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getExpiresIn() {
        return expiresIn;
    }
}
