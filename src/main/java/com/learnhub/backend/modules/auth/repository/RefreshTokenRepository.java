package com.learnhub.backend.modules.auth.repository;

import com.learnhub.backend.modules.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RefreshTokenRepository — Data access interface for RefreshToken entity.
 * Handles database operations for authentication sessions.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(Long userId);
}
