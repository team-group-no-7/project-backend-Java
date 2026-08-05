package com.learnhub.backend.modules.user.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.UserProfileResponse;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * UserController — REST Controller for User Profile Module.
 * Dedicated package area for User Profile & Settings Management.
 */
@RestController
@RequestMapping("/api/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    // Explicit constructor for dependency injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * GET /api/users/status
     * Health check for User module.
     */
    @GetMapping("/status")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("User Profile Module is Active", "OK"));
    }

    /*
     * GET /api/users/all
     * Fetch all users in the system.
     */
    @GetMapping("/all")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getAllUsers()));
    }

    /*
     * GET /api/users/profile
     * Retrieve the authenticated user's profile (RESTful self-resource).
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUserProfile() {
        String email = com.learnhub.backend.common.util.SecurityUtils.getCurrentUserEmail();
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new com.learnhub.backend.common.exception.ResourceNotFoundException("User context not found"));
        UserProfileResponse profile = userService.getUserProfile(user.getId());
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    /*
     * PUT /api/users/profile
     * Update the authenticated user's profile (RESTful self-resource).
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateCurrentUserProfile(
            @Valid @RequestBody UpdateProfileRequest request) {
        String email = com.learnhub.backend.common.util.SecurityUtils.getCurrentUserEmail();
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new com.learnhub.backend.common.exception.ResourceNotFoundException("User context not found"));
        UserProfileResponse updatedProfile = userService.updateUserProfile(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", updatedProfile));
    }

    /*
     * GET /api/users/{id}
     * View user profile by user ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@PathVariable Long id) {
        UserProfileResponse profile = userService.getUserProfile(id);
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    /*
     * PUT /api/users/{id}
     * Update user profile information (name, headline, location, avatarUrl).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request) {
        UserProfileResponse updatedProfile = userService.updateUserProfile(id, request);
        return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", updatedProfile));
    }

    /*
     * PATCH /api/users/{id}/become-creator
     * Upgrades a user from LEARNER role to CREATOR role in PostgreSQL database.
     */
    @PatchMapping("/{id}/become-creator")
    public ResponseEntity<ApiResponse<UserProfileResponse>> becomeCreator(@PathVariable Long id) {
        UserProfileResponse profile = userService.becomeCreator(id);
        return ResponseEntity.ok(ApiResponse.success("Congratulations! You are now a Creator on LearnHub", profile));
    }
}

