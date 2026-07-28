package com.learnhub.backend.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/*
 * SecurityConfig — The main Spring Security configuration for LearnHub.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity    // It Enables @PreAuthorize("hasRole('ADMIN')") on controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

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
