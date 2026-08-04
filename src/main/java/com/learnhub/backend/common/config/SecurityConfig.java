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
 * SecurityConfig — Main Spring Security configuration for LearnHub.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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

            // Step 2b: Allow frame options for PDF viewer iframe
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // Step 3: Set session management to STATELESS
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Step 4: Define URL access rules
            .authorizeHttpRequests(auth -> auth

                // PUBLIC ENDPOINTS — No JWT token required
                .requestMatchers("/api/auth/**").permitAll()


                // Status check endpoints
                .requestMatchers("/api/users/status").permitAll()
                .requestMatchers("/api/creators/status").permitAll()
                .requestMatchers("/api/creator/content/status").permitAll()
                .requestMatchers("/api/catalog/status").permitAll()
                .requestMatchers("/api/billing/status").permitAll()
                .requestMatchers("/api/mentorship/status").permitAll()
                .requestMatchers("/api/discussion/status").permitAll()

                // Public content, payment, purchases, sessions, admin and file endpoints
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/payment/**").authenticated()
                .requestMatchers("/api/billing/**").authenticated()
                .requestMatchers("/api/contents/**").permitAll()
                .requestMatchers("/api/contents").permitAll()
                .requestMatchers("/api/categories").permitAll()
                .requestMatchers("/api/creators/**").hasRole("CREATOR")
                .requestMatchers("/api/purchases/**").authenticated()
                .requestMatchers("/api/sessions/**").authenticated()
                .requestMatchers("/api/creator/content/**").hasRole("CREATOR")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/qa/**").authenticated()
                .requestMatchers("/uploads/**").permitAll()

                // Swagger/OpenAPI
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()

                // PROTECTED ENDPOINTS — JWT token required for everything else
                .anyRequest().authenticated()
            )

            // Step 5: Add our JWT filter before Spring's default authentication filter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
