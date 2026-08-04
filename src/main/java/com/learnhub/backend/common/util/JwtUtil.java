package com.learnhub.backend.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/*
 * JwtUtil — Utility class for JWT token operations.
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    /*
     * Constructor — reads jwt.secret and jwt.expiration from application.properties.
     */
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generate a JWT token containing the user's email, role, and user ID.
     */
    public String generateToken(String email, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)                    // WHO this token belongs to
                .claim("role", role)               // WHAT role the user has
                .claim("userId", userId)           // Database ID for ownership validation
                .issuedAt(now)                     // WHEN the token was created
                .expiration(expiryDate)            // WHEN the token expires
                .signWith(secretKey)               // SIGN with our secret key
                .compact();                        // BUILD the final token string
    }

    /*
     * Extract the email (subject) from a JWT token.
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extract the role from a JWT token.
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Extract the user ID from a JWT token.
     * Returns null for tokens issued before userId was added to claims.
     */
    public Long extractUserId(String token) {
        Object userId = extractAllClaims(token).get("userId");
        if (userId instanceof Number) {
            return ((Number) userId).longValue();
        }
        return null;
    }

    /*
     * Validate whether a JWT token is valid (not expired and not tampered).
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token is expired, malformed, or signature doesn't match
            return false;
        }
    }

    /*
     * Parse and verify the token, returning all claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
