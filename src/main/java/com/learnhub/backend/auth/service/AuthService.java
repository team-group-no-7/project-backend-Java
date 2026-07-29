package com.learnhub.backend.auth.service;

import com.learnhub.backend.auth.entity.RefreshToken;
import com.learnhub.backend.auth.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * AuthService — Business logic for Authentication, token issuance, and validation.
 *
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@Service
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public AuthService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public List<RefreshToken> getAllTokens() {
        return refreshTokenRepository.findAll();
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> verifyToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(t -> !t.getRevoked() && t.getExpiryDate().isAfter(LocalDateTime.now()));
    }
}
