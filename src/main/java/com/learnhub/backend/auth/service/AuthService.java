package com.learnhub.backend.auth.service;

import com.learnhub.backend.auth.entity.RefreshToken;
import com.learnhub.backend.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * AuthService — Business logic for Authentication, token issuance, and validation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    public List<RefreshToken> getAllTokens() {
        return refreshTokenRepository.findAll();
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> verifyToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(t -> !t.getRevoked() && t.getExpiryDate().isAfter(LocalDateTime.now()));
    }
}
