package com.learnhub.backend.auth.service.impl;

import com.learnhub.backend.auth.dto.AuthResponse;
import com.learnhub.backend.auth.dto.LoginRequest;
import com.learnhub.backend.auth.dto.RegisterRequest;
import com.learnhub.backend.auth.entity.RefreshToken;
import com.learnhub.backend.auth.repository.RefreshTokenRepository;
import com.learnhub.backend.auth.service.AuthService;
import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.JwtUtil;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AuthServiceImpl — Implementation class for Authentication service.
 * 
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public AuthServiceImpl(UserRepository userRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * REGISTER — Create a new user account.
     */
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // Step 1: Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        // Step 2: Create User object using standard constructor & setters
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("LEARNER");

        // Step 3: Save user to database
        User savedUser = userRepository.save(newUser);

        // Step 4: Generate JWT token
        String jwtToken = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole());

        // Step 5: Create a refresh token
        RefreshToken refreshToken = createRefreshToken(savedUser.getId());

        // Step 6: Return AuthResponse using standard constructor
        return new AuthResponse(
                jwtToken,
                refreshToken.getToken(),
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    /**
     * LOGIN — Verify credentials and issue tokens.
     */
    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {

        // Step 1: Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        // Step 2: Compare plain password with stored BCrypt hash
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        // Step 3: Generate JWT token
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // Step 4: Create refresh token
        RefreshToken refreshToken = createRefreshToken(user.getId());

        // Step 5: Return AuthResponse using standard constructor
        return new AuthResponse(
                jwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * REFRESH TOKEN — Issue a new JWT using a valid refresh token.
     */
    @Override
    @Transactional
    public AuthResponse refreshToken(String token) {

        // Step 1: Find the refresh token in database
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        // Step 2: Check if token is revoked
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

        // Step 6: Return response with new JWT
        return new AuthResponse(
                newJwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    /**
     * LOGOUT — Revoke the refresh token.
     */
    @Override
    @Transactional
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * HELPER — Create and save a new refresh token for a user.
     */
    private RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }
}
