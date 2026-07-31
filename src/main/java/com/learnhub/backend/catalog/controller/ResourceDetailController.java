package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.dto.ResourceDetailResponse;
import com.learnhub.backend.catalog.service.ResourceDetailService;
import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ResourceDetailController — Public REST Controller for Single Resource Detail page view.
 * Base URL: /api/public
 *
 * Implemented in pure Java with explicit constructor dependency injection (No Lombok).
 * Consumed directly by React frontend (ResourceDetailPage.jsx).
 */
@RestController
@RequestMapping("/api/public")
public class ResourceDetailController {

    private final ResourceDetailService resourceDetailService;

    // Explicit constructor dependency injection (No Lombok)
    public ResourceDetailController(ResourceDetailService resourceDetailService) {
        this.resourceDetailService = resourceDetailService;
    }

    /**
     * GET /api/public/resource/{id}
     * Returns detailed resource information, creator profile, preview text, and student reviews.
     */
    @GetMapping("/resource/{id}")
    public ResponseEntity<ApiResponse<ResourceDetailResponse>> getResourceDetails(@PathVariable Long id) {
        ResourceDetailResponse details = resourceDetailService.getResourceDetails(id);
        return ResponseEntity.ok(ApiResponse.success("Resource details fetched successfully", details));
    }

    /**
     * GET /api/public/contents/{id}
     * Alias endpoint specified in API documentation handbook.
     */
    @GetMapping("/contents/{id}")
    public ResponseEntity<ApiResponse<ResourceDetailResponse>> getContentDetails(@PathVariable Long id) {
        ResourceDetailResponse details = resourceDetailService.getResourceDetails(id);
        return ResponseEntity.ok(ApiResponse.success("Resource details fetched successfully", details));
    }
}
