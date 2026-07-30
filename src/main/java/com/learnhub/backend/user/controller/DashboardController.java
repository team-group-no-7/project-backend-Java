package com.learnhub.backend.user.controller;

import com.learnhub.backend.user.dto.response.DashboardResponse;
import com.learnhub.backend.user.service.DashboardService;
import org.springframework.web.bind.annotation.*;

/**
 * DashboardController — REST Controller for Learner Dashboard Metrics.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@RestController
@RequestMapping("/api/learners/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{userId}")
    public DashboardResponse dashboard(@PathVariable Long userId) {
        return dashboardService.getDashboard(userId);
    }
}