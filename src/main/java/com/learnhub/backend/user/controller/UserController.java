package com.learnhub.backend.user.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.user.dto.UpdateProfileRequest;
import com.learnhub.backend.user.dto.UserProfileResponse;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController — REST Controller for User Profile Module.
 * Dedicated package area for User Profile & Settings Management.
 *
 * Base Path: /api/users
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Explicit constructor for dependency injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users/status
     * Health check for User module.
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("User Profile Module is Active", "OK"));
    }

    /**
     * GET /api/users/all
     * Fetch all users in the system.
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getAllUsers()));
    }

    /**
     * GET /api/users/{id}
     * View user profile by user ID.
     *
     * @param id the user ID
     * @return UserProfileResponse containing user details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@PathVariable Long id) {
        UserProfileResponse profile = userService.getUserProfile(id);
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully", profile));
    }

    /**
     * PUT /api/users/{id}
     * Update user profile information (name, headline, location, avatarUrl).
     *
     * @param id the user ID
     * @param request JSON body with updated profile fields
     * @return updated UserProfileResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request) {
        UserProfileResponse updatedProfile = userService.updateUserProfile(id, request);
        return ResponseEntity.ok(ApiResponse.success("User profile updated successfully", updatedProfile));
    }
}
