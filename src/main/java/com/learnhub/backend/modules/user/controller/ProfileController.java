package com.learnhub.backend.modules.user.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.response.ProfileResponse;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.modules.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ProfileController — REST Controller for Learner Profile Operations.
 * Refactored with class-level @PreAuthorize and automatic JWT identity resolution.
 */
@RestController
@RequestMapping("/api/learners/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    public ProfileController(ProfileService profileService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/me", "/{userId}"})
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable(required = false) Long userId) {
        Long resolvedUserId = resolveUserId(userId);
        ProfileResponse profile = profileService.getProfile(resolvedUserId);
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", profile));
    }

    @PutMapping({"", "/me", "/{userId}"})
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @PathVariable(required = false) Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        Long resolvedUserId = resolveUserId(userId);
        ProfileResponse updatedProfile = profileService.updateProfile(resolvedUserId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", updatedProfile));
    }

    private Long resolveUserId(Long requestedUserId) {
        String currentEmail = SecurityUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user context not found"));
        if (requestedUserId != null && !SecurityUtils.isAdmin()) {
            SecurityUtils.validateOwnershipById(currentUser.getId(), requestedUserId);
            return requestedUserId;
        }
        return (requestedUserId != null && SecurityUtils.isAdmin()) ? requestedUserId : currentUser.getId();
    }
}