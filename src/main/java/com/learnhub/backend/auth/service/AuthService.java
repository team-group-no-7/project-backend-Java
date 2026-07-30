package com.learnhub.backend.auth.service;

import com.learnhub.backend.auth.dto.AuthResponse;
import com.learnhub.backend.auth.dto.LoginRequest;
import com.learnhub.backend.auth.dto.RegisterRequest;

/**
 * AuthService — Business logic interface for Authentication.
 */
public interface AuthService {

    /**
     * REGISTER — Create a new user account.
     */
    AuthResponse register(RegisterRequest request);

    /**
     * LOGIN — Verify credentials and issue tokens.
     */
    AuthResponse login(LoginRequest request);

    /**
     * REFRESH TOKEN — Issue a new JWT using a valid refresh token.
     */
    AuthResponse refreshToken(String token);

    /**
     * LOGOUT — Revoke the refresh token.
     */
    void logout(String token);
}
