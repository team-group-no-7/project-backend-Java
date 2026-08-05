package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.dto.PlatformStatsResponse;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.enums.ApprovalStatus;
import com.learnhub.backend.modules.resource.service.ContentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * AdminResourceController — Handles administrative content moderation and platform metrics.
 * Uses typed ApprovalStatus enum methods for status updates.
 * Preserves exact path mapping (/api/admin/stats, /api/admin/contents) for frontend compatibility.
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminResourceController {

    private final ContentService contentService;

    public AdminResourceController(ContentService contentService) {
        this.contentService = contentService;
    }

    // GET /api/admin/stats & /api/admin/analytics - Retrieve high-level platform statistics
    @GetMapping({"/stats", "/analytics"})
    public ResponseEntity<ApiResponse<PlatformStatsResponse>> getPlatformStats() {
        PlatformStatsResponse stats = contentService.getPlatformStats();
        return ResponseEntity.ok(ApiResponse.success("Platform statistics retrieved successfully", stats));
    }

    // GET /api/admin/contents - List all contents for moderation
    @GetMapping("/contents")
    public ResponseEntity<ApiResponse<List<ContentResponse>>> getAllContents() {
        List<ContentResponse> contents = contentService.getAllContentResponses();
        return ResponseEntity.ok(ApiResponse.success("Admin contents list retrieved successfully", contents));
    }

    // POST /api/admin/contents/{id}/approve - Approve content upload
    @PostMapping("/contents/{id}/approve")
    public ResponseEntity<ApiResponse<ContentResponse>> approveContent(@PathVariable("id") Long id) {
        ContentResponse content = contentService.updateApprovalStatus(id, ApprovalStatus.APPROVED);
        return ResponseEntity.ok(ApiResponse.success("Content approved successfully", content));
    }

    // POST /api/admin/contents/{id}/flag - Flag/suspend content upload
    @PostMapping("/contents/{id}/flag")
    public ResponseEntity<ApiResponse<ContentResponse>> flagContent(@PathVariable("id") Long id) {
        ContentResponse content = contentService.updateApprovalStatus(id, ApprovalStatus.FLAGGED);
        return ResponseEntity.ok(ApiResponse.success("Content flagged successfully", content));
    }
}
