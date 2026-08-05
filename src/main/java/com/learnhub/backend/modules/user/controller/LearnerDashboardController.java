package com.learnhub.backend.modules.user.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.user.dto.response.DashboardResponse;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.modules.user.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * LearnerDashboardController — REST Controller for Learner Dashboard Metrics.
 * Refactored with class-level @PreAuthorize and automatic JWT identity resolution.
 */
@RestController
@RequestMapping("/api/learners/dashboard")
@PreAuthorize("isAuthenticated()")
public class LearnerDashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public LearnerDashboardController(DashboardService dashboardService, UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/me", "/{userId}"})
    public ResponseEntity<ApiResponse<DashboardResponse>> dashboard(@PathVariable(required = false) Long userId) {
        Long resolvedUserId = resolveUserId(userId);
        DashboardResponse response = dashboardService.getDashboard(resolvedUserId);
        return ResponseEntity.ok(ApiResponse.success("Learner dashboard retrieved successfully", response));
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