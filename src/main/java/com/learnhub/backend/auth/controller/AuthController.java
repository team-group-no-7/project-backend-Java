package com.learnhub.backend.auth.controller;

import com.learnhub.backend.auth.dto.AuthResponse;
import com.learnhub.backend.auth.dto.LoginRequest;
import com.learnhub.backend.auth.dto.RefreshTokenRequest;
import com.learnhub.backend.auth.dto.RegisterRequest;
import com.learnhub.backend.auth.service.AuthService;
import com.learnhub.backend.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController — REST Controller for Authentication Module.
 * Exposes REST API endpoints for user registration, login, token refresh, and logout.
 *
 * Base Path: /api/auth
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * GET /api/auth/status
     * Health check endpoint to verify auth module is running.
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Authentication Module is Active", "OK"));
    }

    /**
     * POST /api/auth/register
     * Register a new user account.
     *
     * @param request JSON body with name, email, password
     * @return AuthResponse containing JWT token, refresh token, and user details
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", authResponse));
    }

    /**
     * POST /api/auth/login
     * Authenticate existing user with email and password.
     *
     * @param request JSON body with email and password
     * @return AuthResponse containing JWT token, refresh token, and user details
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }

    /**
     * POST /api/auth/refresh
     * Generate a new access JWT token using a valid refresh token.
     *
     * @param request JSON body with refreshToken
     * @return AuthResponse containing new JWT token
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse authResponse = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", authResponse));
    }

    /**
     * POST /api/auth/logout
     * Revoke the user's refresh token on logout.
     *
     * @param request JSON body with refreshToken
     * @return Success message
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully", null));
    }
}
