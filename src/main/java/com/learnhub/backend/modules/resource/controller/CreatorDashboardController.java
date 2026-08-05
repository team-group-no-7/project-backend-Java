package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.modules.resource.dto.CreatorDashboardStatsDto;
import com.learnhub.backend.modules.resource.service.CreatorDashboardService;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * CreatorDashboardController — REST Controller for Creator Analytics & Metrics.
 */
@RestController
@RequestMapping("/api/creators")
public class CreatorDashboardController {

    private final CreatorDashboardService creatorDashboardService;
    private final UserRepository userRepository;

    // Explicit constructor for dependency injection
    public CreatorDashboardController(CreatorDashboardService creatorDashboardService, UserRepository userRepository) {
        this.creatorDashboardService = creatorDashboardService;
        this.userRepository = userRepository;
    }

    /**
     * GET /api/creators/status
     * Health check endpoint for Creator Dashboard module.
     */
    @GetMapping("/status")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Creator Dashboard Module is Active", "OK"));
    }

    /*
     * GET /api/creators/{creatorId}/dashboard-stats
     * Fetch analytics metrics for a creator after verifying ownership or admin authority.
     */
    @GetMapping("/{creatorId}/dashboard-stats")
    @PreAuthorize("hasRole('CREATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreatorDashboardStatsDto>> getDashboardStats(@PathVariable Long creatorId) {
        SecurityUtils.validateOwnershipByUserId(creatorId, userRepository);
        CreatorDashboardStatsDto stats = creatorDashboardService.getDashboardStats(creatorId);
        return ResponseEntity.ok(ApiResponse.success("Creator dashboard statistics retrieved successfully", stats));
    }
}
