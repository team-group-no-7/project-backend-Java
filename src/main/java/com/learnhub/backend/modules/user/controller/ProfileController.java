package com.learnhub.backend.modules.user.controller;

import com.learnhub.backend.modules.user.dto.UpdateProfileRequest;
import com.learnhub.backend.modules.user.dto.response.ProfileResponse;
import com.learnhub.backend.modules.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * ProfileController — REST Controller for Learner Profile Operations.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@RestController
@RequestMapping("/api/learners/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ProfileResponse getProfile(@PathVariable Long userId) {
        return profileService.getProfile(userId);
    }

    @PutMapping("/{userId}")
    public ProfileResponse updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        return profileService.updateProfile(userId, request);
    }
}