package com.learnhub.backend.user.controller;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import com.learnhub.backend.catalog.dto.ContentResponse;
import com.learnhub.backend.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * PublicCreatorProfileController — Exposes REST endpoints to view public creator details and their courses/guides.
 */
@RestController
@RequestMapping("/api/creators")
public class PublicCreatorProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogService catalogService;

    // GET /api/creators/{id} - Fetch public profile of a creator
    @GetMapping("/{id}")
    public ResponseEntity<User> getCreatorProfile(@PathVariable("id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creator profile not found with id: " + id));
        
        // Return 404/Error if the user is not a Creator
        if (!"CREATOR".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Specified user is not registered as a creator");
        }
        
        return ResponseEntity.ok(user);
    }

    // GET /api/creators/{id}/contents - Get all study guides published by this creator
    @GetMapping("/{id}/contents")
    public ResponseEntity<List<ContentResponse>> getCreatorContents(@PathVariable("id") Long id) {
        List<ContentResponse> contents = catalogService.getContentsByCreator(id);
        return ResponseEntity.ok(contents);
    }
}
