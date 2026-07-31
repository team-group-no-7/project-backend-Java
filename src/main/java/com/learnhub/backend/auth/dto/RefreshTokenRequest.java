package com.learnhub.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * RefreshTokenRequest — Carries refresh token string for refresh and logout endpoints.
 */
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    // Default Constructor (Required by Jackson)
    public RefreshTokenRequest() {
    }

    // Parameterized Constructor
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getter and Setter
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
