package com.learnhub.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * RefreshTokenRequest — Carries refresh token string for refresh and logout endpoints.
 */
@Data
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
