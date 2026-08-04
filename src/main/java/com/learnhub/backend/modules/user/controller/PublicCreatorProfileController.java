package com.learnhub.backend.modules.user.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.user.dto.UserProfileResponse;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * PublicCreatorProfileController — Exposes REST endpoints to view public creator details and their courses/guides.
 */
@RestController
@RequestMapping("/api/creators")
public class PublicCreatorProfileController {

    private final UserRepository userRepository;
    private final CatalogService catalogService;

    public PublicCreatorProfileController(UserRepository userRepository, CatalogService catalogService) {
        this.userRepository = userRepository;
        this.catalogService = catalogService;
    }

    // GET /api/creators/{id} - Fetch public profile of a creator
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCreatorProfile(@PathVariable("id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found with id: " + id));
        
        // Return error if the user is not a Creator
        if (user.getRole() == null || !user.getRole().contains("CREATOR")) {
            throw new BadRequestException("Specified user is not registered as a creator");
        }
        
        UserProfileResponse profileResponse = new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getAvatarUrl(),
                user.getHeadline(),
                user.getLocation(),
                user.getJoinedAt()
        );

        return ResponseEntity.ok(ApiResponse.success("Creator profile retrieved successfully", profileResponse));
    }

    // GET /api/creators/{id}/contents - Get all study guides published by this creator
    @GetMapping("/{id}/contents")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getCreatorContents(@PathVariable("id") Long id) {
        List<ContentResponse> contents = catalogService.getContentsByCreator(id);
        return ResponseEntity.ok(ApiResponse.success("Creator resources retrieved successfully", contents));
    }
}
