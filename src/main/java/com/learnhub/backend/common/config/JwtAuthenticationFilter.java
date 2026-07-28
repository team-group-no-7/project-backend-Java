package com.learnhub.backend.common.config;

import com.learnhub.backend.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/*
 * JwtAuthenticationFilter — Runs before every HTTP request to check for a valid JWT token.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Read the Authorization header from the incoming request
        String authHeader = request.getHeader("Authorization");

        // Step 2: Check if the header exists and starts with "Bearer "
        // If not, skip this filter — the request is either public or unauthenticated
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3: Extract the token (remove "Bearer " prefix)
        // "Bearer eyJhbGciOiJIUzI1NiJ9..." → "eyJhbGciOiJIUzI1NiJ9..."
        String token = authHeader.substring(7);

        // Step 4: Validate the token using JwtUtil
        if (jwtUtil.isTokenValid(token)) {

            // Step 5: Extract user details from the token
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            // Step 6: Create a Spring Security Authentication object
            // SimpleGrantedAuthority("ROLE_LEARNER") tells Spring Security what role this user has
            // This enables @PreAuthorize("hasRole('ADMIN')") checks on controllers
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,                                       // principal (who is this user)
                            null,                                        // credentials (not needed, we already verified)
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))  // authorities (user's role)
                    );

            // Step 7: Set the authentication in Spring Security's context
            // After this line, Spring Security considers this request "authenticated"
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Step 8: Continue the filter chain — pass the request to the next filter/controller
        filterChain.doFilter(request, response);
    }
}
