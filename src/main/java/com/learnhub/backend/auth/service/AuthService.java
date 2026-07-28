package com.learnhub.backend.auth.service;

import com.learnhub.backend.auth.dto.AuthResponse;
import com.learnhub.backend.auth.dto.LoginRequest;
import com.learnhub.backend.auth.dto.RegisterRequest;
import com.learnhub.backend.auth.entity.RefreshToken;
import com.learnhub.backend.auth.repository.RefreshTokenRepository;
import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.JwtUtil;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AuthService — Contains all the business logic for Authentication.
 *
 * This is the brain of the auth module. It handles:
 * 1. Register — Create a new user account
 * 2. Login — Verify credentials and issue tokens
 * 3. Refresh — Issue a new JWT using a valid refresh token
 * 4. Logout — Revoke the refresh token so it can't be reused
 *
 * WHY SERVICE LAYER:
 * - Controllers should only handle HTTP request/response.
 * - All business logic (validation, password hashing, token creation) lives here.
 * - This makes the code testable — you can unit test AuthService without a web server.
 *
 * CROSS-MODULE COMMUNICATION:
 * - AuthService needs UserRepository from the "user" module.
 * - We inject it directly because we are still a monolith.
 * - If we extract auth into a microservice later, we replace this with a REST call.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    // From user module — we need to save and find users
    private final UserRepository userRepository;

    // From auth module — we need to save and find refresh tokens
    private final RefreshTokenRepository refreshTokenRepository;

    // From common module — we need to generate and validate JWT tokens
    private final JwtUtil jwtUtil;

    // From common config — we need to hash and verify passwords
    private final PasswordEncoder passwordEncoder;

    /**
     * REGISTER — Create a new user account.
     *
     * Flow:
     * 1. Check if email already exists in database
     * 2. Hash the plain text password using BCrypt
     * 3. Save the new user to database
     * 4. Generate a JWT token for immediate login
     * 5. Create a refresh token for session renewal
     * 6. Return AuthResponse with token + user info
     *
     * @param request contains name, email, password from the registration form
     * @return AuthResponse with JWT token and user details
     * @throws BadRequestException if email is already registered
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // Step 1: Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        // Step 2: Build a new User entity with hashed password
        // Role defaults to "LEARNER" — we don't let users choose ADMIN from the form!
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))  // Hash the password
                .role("LEARNER")
                .build();

        // Step 3: Save user to database — JPA auto-generates the ID
        User savedUser = userRepository.save(newUser);

        // Step 4: Generate JWT token with email and role
        String jwtToken = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole());

        // Step 5: Create a refresh token for this user
        RefreshToken refreshToken = createRefreshToken(savedUser.getId());

        // Step 6: Build and return the response
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    /**
     * LOGIN — Verify user credentials and issue tokens.
     *
     * Flow:
     * 1. Find user by email
     * 2. Compare provided password with stored BCrypt hash
     * 3. Generate JWT token
     * 4. Create refresh token
     * 5. Return AuthResponse
     *
     * @param request contains email and password from the login form
     * @return AuthResponse with JWT token and user details
     * @throws BadRequestException if email not found or password is wrong
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {

        // Step 1: Find user by email — throw error if not found
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        // Step 2: Compare plain password with stored BCrypt hash
        // passwordEncoder.matches("pass123", "$2a$10$abc...") → true/false
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        // Step 3: Generate JWT token
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // Step 4: Create refresh token
        RefreshToken refreshToken = createRefreshToken(user.getId());

        // Step 5: Build and return the response
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /**
     * REFRESH TOKEN — Issue a new JWT using a valid refresh token.
     *
     * WHY REFRESH TOKENS EXIST:
     * - JWT tokens expire after 24 hours (for security).
     * - Instead of forcing the user to login again, the frontend sends the
     *   refresh token to get a new JWT without re-entering credentials.
     * - Refresh tokens have a longer lifespan (7 days).
     *
     * @param token the refresh token string sent by the frontend
     * @return AuthResponse with a new JWT token
     * @throws BadRequestException if refresh token is invalid, expired, or revoked
     */
    @Transactional
    public AuthResponse refreshToken(String token) {

        // Step 1: Find the refresh token in database
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        // Step 2: Check if token is revoked (user logged out)
        if (refreshToken.getRevoked()) {
            throw new BadRequestException("Refresh token has been revoked");
        }

        // Step 3: Check if token is expired
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Refresh token has expired");
        }

        // Step 4: Find the user who owns this token
        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Step 5: Generate a new JWT token
        String newJwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // Step 6: Return response with new JWT (same refresh token stays valid)
        return AuthResponse.builder()
                .token(newJwtToken)
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /**
     * LOGOUT — Revoke the refresh token so it cannot be reused.
     *
     * WHY:
     * - JWT tokens are stateless — once issued, the server can't invalidate them
     *   until they expire naturally.
     * - But we CAN invalidate the refresh token, so the user can't get new JWTs.
     * - The existing JWT will expire on its own after 24 hours.
     *
     * @param token the refresh token string to revoke
     */
    @Transactional
    public void logout(String token) {

        // Find the refresh token and mark it as revoked
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * HELPER — Create and save a new refresh token for a user.
     *
     * Uses UUID for a unique, random token string.
     * Refresh token is valid for 7 days.
     *
     * @param userId the ID of the user to create the token for
     * @return the saved RefreshToken entity
     */
    private RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
}
