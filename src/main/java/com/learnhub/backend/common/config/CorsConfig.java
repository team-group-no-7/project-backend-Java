package com.learnhub.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * CorsConfig — Allows the React frontend to communicate with this Spring Boot backend.
 *
 * WHAT IS CORS:
 * - CORS = Cross-Origin Resource Sharing
 * - By default, browsers block requests from one origin (localhost:5173)
 *   to another origin (localhost:8080) for security reasons.
 * - This configuration tells Spring Boot: "Allow requests from our React app."
 *
 * WITHOUT THIS FILE:
 * - The React frontend would get this browser error:
 *   "Access to XMLHttpRequest at 'http://localhost:8080/api/auth/login'
 *    from origin 'http://localhost:5173' has been blocked by CORS policy"
 *
 * INTERVIEW TIP:
 * - "CORS is a browser-level security mechanism. It doesn't affect Postman or
 *    server-to-server calls. We configured it to allow our React frontend's
 *    origin while keeping other origins blocked."
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // Which frontend origins are allowed to make requests
        // localhost:5173 = Vite dev server (React)
        // localhost:3000 = Alternative React dev server
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:3000"
        ));

        // Which HTTP methods are allowed
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Which request headers are allowed
        // "Authorization" is needed because the frontend sends JWT in this header
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Allow the browser to include cookies/auth headers in cross-origin requests
        config.setAllowCredentials(true);

        // Apply this CORS configuration to ALL API endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
