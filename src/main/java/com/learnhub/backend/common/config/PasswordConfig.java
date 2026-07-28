package com.learnhub.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordConfig — Provides the PasswordEncoder bean for the entire application.
 *
 * WHY THIS EXISTS:
 * - We never store plain text passwords in the database ("pass123").
 * - BCrypt is a one-way hashing algorithm — it converts "pass123" into something like
 *   "$2a$10$dXJ3SW6G7P50lGmMQoeqhOu..." which cannot be reversed.
 * - During login, BCrypt compares the plain password with the stored hash.
 *
 * WHY SEPARATE CONFIG FILE:
 * - If we put this @Bean inside SecurityConfig, it can cause circular dependency errors
 *   because SecurityConfig also needs PasswordEncoder. Keeping it separate avoids that.
 *
 * INTERVIEW TIP:
 * - "We use BCrypt because it includes a salt by default, making it resistant to
 *    rainbow table attacks. Each hash is unique even for the same input password."
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
