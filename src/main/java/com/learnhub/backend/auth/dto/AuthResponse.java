package com.learnhub.backend.auth.dto;

/**
 * AuthResponse — The response sent back to the frontend after successful login or register.
 *
 * After a user logs in or registers, the backend returns this JSON:
 * {
 *   "token": "eyJhbGciOiJIUzI1NiJ9...",
 *   "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
 *   "id": 101,
 *   "name": "Arjun Mehta",
 *   "email": "arjun@learnhub.com",
 *   "role": "LEARNER"
 * }
 *
 * The React frontend stores the "token" in localStorage and sends it
 * with every future API request in the Authorization header:
 *   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
 *
 * WHY WE DON'T RETURN THE USER ENTITY DIRECTLY:
 * - The User entity contains the password field (even if hashed).
 * - We should never send passwords back to the frontend.
 * - This DTO gives us control over exactly which fields the frontend receives.
 *
 * IMPLEMENTATION NOTE (CDAC PGCP-AC):
 * Implemented in pure Java with explicit getters, setters, and constructors.
 */
public class AuthResponse {

    private String token;
    private String refreshToken;
    private Long id;
    private String name;
    private String email;
    private String role;

    // Default Constructor (Required by Jackson for JSON deserialization)
    public AuthResponse() {
    }

    // Parameterized Constructor
    public AuthResponse(String token, String refreshToken, Long id, String name, String email, String role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
