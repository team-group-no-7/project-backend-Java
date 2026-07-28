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

/**
 * JwtUtil — Utility class for JWT token operations.
 *
 * Responsibilities:
 * 1. Generate a JWT token (after successful login/register)
 * 2. Extract the user's email from a token
 * 3. Extract the user's role from a token
 * 4. Validate whether a token is still valid (not expired, not tampered)
 *
 * WHY THIS EXISTS:
 * - JWT (JSON Web Token) is an industry-standard way to handle stateless authentication.
 * - Instead of storing sessions on the server (like HttpSession), the server issues a
 *   signed token to the client. The client sends this token with every request.
 * - The server verifies the token's signature without needing a database lookup.
 *
 * HOW IT WORKS:
 * - Token = Header.Payload.Signature
 * - Header: Algorithm (HS256) + Type (JWT)
 * - Payload: Claims (email, role, issued-at, expiration)
 * - Signature: HMAC-SHA256(header + payload, secretKey)
 *
 * INTERVIEW TIP:
 * - "We chose JWT over session-based auth because our frontend is a React SPA
 *    making cross-origin API calls. JWT is stateless, scales horizontally,
 *    and works seamlessly with CORS."
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    /**
     * Constructor — reads jwt.secret and jwt.expiration from application.properties.
     *
     * WHY @Value INJECTION:
     * - Keeps secrets out of Java source code.
     * - In production, override via environment variables (JWT_SECRET=...).
     */
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generate a JWT token containing the user's email and role.
     *
     * @param email the user's email (used as the "subject" claim)
     * @param role  the user's role (LEARNER, CREATOR, ADMIN)
     * @return signed JWT token string
     */
    public String generateToken(String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)                    // WHO this token belongs to
                .claim("role", role)               // WHAT role the user has
                .issuedAt(now)                     // WHEN the token was created
                .expiration(expiryDate)            // WHEN the token expires
                .signWith(secretKey)               // SIGN with our secret key
                .compact();                        // BUILD the final token string
    }

    /**
     * Extract the email (subject) from a JWT token.
     *
     * @param token the JWT token string
     * @return the email embedded in the token
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extract the role from a JWT token.
     *
     * @param token the JWT token string
     * @return the role embedded in the token (LEARNER, CREATOR, ADMIN)
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Validate whether a JWT token is valid (not expired and not tampered).
     *
     * @param token the JWT token string
     * @return true if the token is valid, false otherwise
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

    /**
     * Parse and verify the token, returning all claims.
     *
     * INTERNAL METHOD — used by extractEmail(), extractRole(), and isTokenValid().
     *
     * If the token is invalid (expired, wrong signature, malformed),
     * this method throws a JwtException which is caught by isTokenValid().
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
