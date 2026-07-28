package com.learnhub.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String refreshToken;
    private Long id;
    private String name;
    private String email;
    private String role;
}
