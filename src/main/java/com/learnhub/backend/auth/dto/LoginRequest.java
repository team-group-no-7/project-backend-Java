package com.learnhub.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * LoginRequest — Carries data from the frontend login form to the backend.
 *
 * When a user fills the Login form on React and clicks "Sign In",
 * the frontend sends a POST request to /api/auth/login with this JSON body:
 * {
 *   "email": "arjun@learnhub.com",
 *   "password": "pass123"
 * }
 *
 * Spring Boot automatically converts that JSON into this Java object.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
