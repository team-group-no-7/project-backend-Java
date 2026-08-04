package com.learnhub.backend.common.util;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityUtils — Utility class for common security operations.
 * Provides static methods for retrieving the authenticated user's context
 * and validating resource ownership.
 */
public final class SecurityUtils {

    private SecurityUtils() {
        // Utility class — prevent instantiation
    }

    /**
     * Returns the email (principal name) of the currently authenticated user.
     * Throws AccessDeniedException if no valid authentication exists.
     */
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new AccessDeniedException("Authentication required");
        }
        return authentication.getName();
    }

    /**
     * Returns true if the currently authenticated user has ROLE_ADMIN authority.
     */
    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    /**
     * Validates that the authenticated user's email matches the resource owner's email,
     * or that the authenticated user has ROLE_ADMIN (admins bypass ownership checks).
     * Throws AccessDeniedException if ownership validation fails.
     */
    public static void validateOwnership(String ownerEmail) {
        if (isAdmin()) {
            return;
        }
        String currentEmail = getCurrentUserEmail();
        if (!currentEmail.equals(ownerEmail)) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
    }

    /**
     * Validates that the authenticated user's ID matches the requested resource owner's ID,
     * or that the authenticated user has ROLE_ADMIN.
     * Throws AccessDeniedException if ownership validation fails.
     */
    public static void validateOwnershipById(Long authenticatedUserId, Long requestedUserId) {
        if (isAdmin()) {
            return;
        }
        if (authenticatedUserId == null || !authenticatedUserId.equals(requestedUserId)) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
    }

    /**
     * Validates that the authenticated user's email matches the requested user ID's email.
     * Looks up user email via UserRepository. If user not found, allows downstream service to handle 404.
     */
    public static void validateOwnershipByUserId(Long requestedUserId, com.learnhub.backend.modules.user.repository.UserRepository userRepository) {
        if (isAdmin()) {
            return;
        }
        String currentEmail = getCurrentUserEmail();
        userRepository.findById(requestedUserId).ifPresent(user -> {
            if (!currentEmail.equals(user.getEmail())) {
                throw new AccessDeniedException("You are not authorized to access this resource");
            }
        });
    }
}
