package com.learnhub.backend.modules.auth.service;

import com.learnhub.backend.modules.auth.dto.AuthResponse;
import com.learnhub.backend.modules.auth.dto.LoginRequest;
import com.learnhub.backend.modules.auth.dto.RegisterRequest;

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
