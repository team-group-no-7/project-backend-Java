package com.learnhub.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
 *
 * IMPLEMENTATION NOTE (CDAC PGCP-AC):
 * Implemented in pure Java with explicit getters, setters, and constructors.
 */
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // Default Constructor (Required by Jackson for JSON deserialization)
    public LoginRequest() {
    }

    // Parameterized Constructor
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                '}';
    }
}
