package com.learnhub.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

/**
 * SecurityConfig — Configures web security, disabling CSRF to allow REST requests (POST/DELETE),
 * enabling CORS to allow frontend communication, and setting up authentication rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS using the configuration source bean defined below
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Disable CSRF protection since REST APIs are stateless
            .csrf(csrf -> csrf.disable())
            
            // Configure request authorization
            .authorizeHttpRequests(auth -> auth
                // Allow public unauthenticated GET access for browsing the catalog
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/catalog/**", "/api/contents/**", "/api/categories/**", "/api/creators/**").permitAll()
                // Permit registration and login
                .requestMatchers("/api/auth/**").permitAll()
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            
            // Enable HTTP Basic authentication for testing via Postman
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all standard local React frontend dev server origins
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000", 
            "http://localhost:5173", 
            "http://localhost:5174",
            "http://localhost:5175"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
