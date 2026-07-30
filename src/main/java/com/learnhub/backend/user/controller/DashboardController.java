package com.learnhub.backend.user.controller;

import com.learnhub.backend.user.dto.response.DashboardResponse;
import com.learnhub.backend.user.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learners/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping("/{userId}")
    public DashboardResponse dashboard(
            @PathVariable Long userId) {

        return dashboardService.getDashboard(userId);
    }
}