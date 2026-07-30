package com.learnhub.backend.catalog.controller;

import com.learnhub.backend.catalog.dto.CreatorDashboardStatsDto;
import com.learnhub.backend.catalog.service.CreatorDashboardService;
import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * CreatorDashboardController — REST Controller for Creator Analytics & Metrics.
 */
@RestController
@RequestMapping("/api/creators")
public class CreatorDashboardController {

    private final CreatorDashboardService creatorDashboardService;

    // Explicit constructor for dependency injection
    public CreatorDashboardController(CreatorDashboardService creatorDashboardService) {
        this.creatorDashboardService = creatorDashboardService;
    }

    /**
     * GET /api/creators/status
     * Health check endpoint for Creator Dashboard module.
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Creator Dashboard Module is Active", "OK"));
    }

    /*
     * GET /api/creators/{creatorId}/dashboard-stats
     * Fetch analytics metrics (total resources, total learners, total earnings) for a creator.
     */
    @GetMapping("/{creatorId}/dashboard-stats")
    public ResponseEntity<ApiResponse<CreatorDashboardStatsDto>> getDashboardStats(@PathVariable Long creatorId) {
        CreatorDashboardStatsDto stats = creatorDashboardService.getDashboardStats(creatorId);
        return ResponseEntity.ok(ApiResponse.success("Creator dashboard statistics retrieved successfully", stats));
    }
}
