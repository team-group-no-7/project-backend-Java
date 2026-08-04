package com.learnhub.backend.modules.auth.service.impl;

import com.learnhub.backend.modules.auth.dto.AuthResponse;
import com.learnhub.backend.modules.auth.dto.LoginRequest;
import com.learnhub.backend.modules.auth.dto.RegisterRequest;
import com.learnhub.backend.modules.auth.entity.RefreshToken;
import com.learnhub.backend.modules.auth.repository.RefreshTokenRepository;
import com.learnhub.backend.modules.auth.service.AuthService;
import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.JwtUtil;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AuthServiceImpl — Implementation class for Authentication service with SLF4J structured logging.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Processing user registration attempt for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed. Email already registered: {}", request.getEmail());
            throw new BadRequestException("Email already registered");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("LEARNER");

        User savedUser = userRepository.save(newUser);
        log.info("Successfully created user account with ID: {} and role: {}", savedUser.getId(), savedUser.getRole());

        String jwtToken = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole(), savedUser.getId());
        RefreshToken refreshToken = createRefreshToken(savedUser.getId());

        return new AuthResponse(
                jwtToken,
                refreshToken.getToken(),
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Processing login request for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed. User not found for email: {}", request.getEmail());
                    return new BadRequestException("Invalid email or password");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed. Password mismatch for user ID: {}", user.getId());
            throw new BadRequestException("Invalid email or password");
        }

        if ("FROZEN".equalsIgnoreCase(user.getStatus()) || "SUSPENDED".equalsIgnoreCase(user.getStatus())) {
            log.warn("Login blocked. Account is frozen for user ID: {}", user.getId());
            throw new BadRequestException("Your account has been frozen by an administrator. Please contact support.");
        }

        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        RefreshToken refreshToken = createRefreshToken(user.getId());

        log.info("User ID: {} logged in successfully with role: {}", user.getId(), user.getRole());

        return new AuthResponse(
                jwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String token) {
        log.info("Processing token refresh request");

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Token refresh failed. Invalid refresh token provided.");
                    return new BadRequestException("Invalid refresh token");
                });

        if (refreshToken.getRevoked()) {
            log.warn("Token refresh failed. Refresh token is revoked for user ID: {}", refreshToken.getUserId());
            throw new BadRequestException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Token refresh failed. Refresh token expired for user ID: {}", refreshToken.getUserId());
            throw new BadRequestException("Refresh token has expired");
        }

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String newJwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        log.info("Issued new JWT token for user ID: {}", user.getId());

        return new AuthResponse(
                newJwtToken,
                refreshToken.getToken(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    @Transactional
    public void logout(String token) {
        log.info("Processing logout request");
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
        log.info("Successfully revoked refresh token for user ID: {}", refreshToken.getUserId());
    }

    private RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }
}
