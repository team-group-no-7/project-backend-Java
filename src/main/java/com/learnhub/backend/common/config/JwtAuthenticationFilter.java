package com.learnhub.backend.common.config;

import com.learnhub.backend.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtAuthenticationFilter — Runs before every HTTP request to check for a valid JWT token.
 * Supports single or multi-role tokens (e.g. "LEARNER,CREATOR").
 * 
 * Implemented using explicit Java constructor dependency injection (no Lombok).
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Read the Authorization header from the incoming request
        String authHeader = request.getHeader("Authorization");

        // Step 2: Check if the header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3: Extract the token (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Step 4: Validate the token using JwtUtil
        if (jwtUtil.isTokenValid(token)) {

            // Step 5: Extract user details from the token
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            // Parse single or comma-separated roles into GrantedAuthority list
            List<GrantedAuthority> authorities = Arrays.stream(role.split(","))
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.trim()))
                    .collect(Collectors.toList());

            // Step 6: Create a Spring Security Authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authorities
                    );

            // Step 7: Set the authentication in Spring Security's context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Step 8: Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
