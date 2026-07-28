package com.learnhub.backend.auth.controller;

import com.learnhub.backend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController — Handles authentication, login, token issuance, and logout.
 * Full endpoints (register, login, refresh, logout) will be added in Phase 5.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/status")
    public String getStatus() {
        return "Authentication Module is Active";
    }
}

