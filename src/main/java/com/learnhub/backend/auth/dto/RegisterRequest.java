package com.learnhub.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * RegisterRequest — Carries data from the frontend registration form to the backend.
 *
 * When a new user fills the Register form on React and clicks "Sign Up",
 * the frontend sends a POST request to /api/auth/register with this JSON body:
 * {
 *   "name": "Arjun Mehta",
 *   "email": "arjun@learnhub.com",
 *   "password": "pass123"
 * }
 *
 * Spring Boot automatically converts that JSON into this Java object.
 * The @Valid annotation on the controller triggers the validation rules below.
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
