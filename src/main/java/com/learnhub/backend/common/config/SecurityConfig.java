package com.learnhub.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * SecurityConfig — The main Spring Security configuration for LearnHub.
 *
 * WHAT THIS FILE DOES:
 * 1. Disables CSRF (not needed for stateless JWT APIs)
 * 2. Makes the session STATELESS (each request carries its own JWT token)
 * 3. Defines which URLs are PUBLIC (no login needed) vs PROTECTED (JWT required)
 * 4. Adds our JwtAuthenticationFilter before Spring's default filter
 *
 * WHY CSRF IS DISABLED:
 * - CSRF protection is for cookie-based sessions (like JSP/Thymeleaf apps).
 * - Our React frontend sends JWT in the Authorization header, not cookies.
 * - So CSRF attacks are not possible in our architecture.
 *
 * WHY STATELESS SESSION:
 * - Traditional web apps store user sessions on the server (HttpSession).
 * - We don't do that. Each request carries a JWT token that contains all user info.
 * - The server doesn't remember anything between requests — that's "stateless".
 *
 * INTERVIEW TIP:
 * - "We configured Spring Security with stateless sessions because our frontend
 *    is a React SPA that communicates via REST APIs with JWT tokens.
 *    This makes the backend horizontally scalable — any server instance
 *    can handle any request without shared session storage."
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity    // Enables @PreAuthorize("hasRole('ADMIN')") on controllers
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CorsConfigurationSource corsConfigurationSource) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Step 1: Enable CORS — uses our CorsConfig bean
            .cors(cors -> cors.configurationSource(corsConfigurationSource))

            // Step 2: Disable CSRF — not needed for stateless JWT APIs
            .csrf(csrf -> csrf.disable())

            // Step 3: Set session management to STATELESS
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Step 4: Define URL access rules
            .authorizeHttpRequests(auth -> auth

                // PUBLIC ENDPOINTS — No JWT token required
                // Auth endpoints (login, register, refresh) must be accessible without a token
                .requestMatchers("/api/auth/**").permitAll()

                // Status check endpoints — useful for health monitoring
                .requestMatchers("/api/users/status").permitAll()
                .requestMatchers("/api/catalog/status").permitAll()
                .requestMatchers("/api/billing/status").permitAll()
                .requestMatchers("/api/mentorship/status").permitAll()
                .requestMatchers("/api/discussion/status").permitAll()

                // Public content endpoints — Landing page, Marketplace, Resource details
                // These will be created by Sakshi and Shubham later
                .requestMatchers("/api/public/**").permitAll()

                // PROTECTED ENDPOINTS — JWT token required for everything else
                .anyRequest().authenticated()
            )

            // Step 5: Add our JWT filter before Spring's default authentication filter
            // This means our filter runs FIRST, extracts the token, and sets the user
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
