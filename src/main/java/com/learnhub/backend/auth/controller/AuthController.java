package com.learnhub.backend.auth.controller;

import com.learnhub.backend.auth.entity.RefreshToken;
import com.learnhub.backend.auth.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AuthController — Handles authentication, login, token issuance, and logout.
 * Dedicated package area for Authentication Module.
 *
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Authentication Module is Active";
    }

    @GetMapping("/tokens")
    public List<RefreshToken> getAllTokens() {
        return authService.getAllTokens();
    }
}
