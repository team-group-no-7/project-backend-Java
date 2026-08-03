package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.PlatformStatsResponse;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.enums.ApprovalStatus;
import com.learnhub.backend.modules.resource.service.ContentService;
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
public class AdminResourceController {

    private final ContentService contentService;

    public AdminResourceController(ContentService contentService) {
        this.contentService = contentService;
    }

    // GET /api/admin/stats & /api/admin/analytics - Retrieve high-level platform statistics
    @GetMapping({"/stats", "/analytics"})
    public ResponseEntity<PlatformStatsResponse> getPlatformStats() {
        PlatformStatsResponse stats = contentService.getPlatformStats();
        return ResponseEntity.ok(stats);
    }

    // GET /api/admin/contents - List all contents for moderation
    @GetMapping("/contents")
    public ResponseEntity<List<ContentResponse>> getAllContents() {
        List<ContentResponse> contents = contentService.getAllContentResponses();
        return ResponseEntity.ok(contents);
    }

    // POST /api/admin/contents/{id}/approve - Approve content upload
    @PostMapping("/contents/{id}/approve")
    public ResponseEntity<ContentResponse> approveContent(@PathVariable("id") Long id) {
        ContentResponse content = contentService.updateApprovalStatus(id, ApprovalStatus.APPROVED);
        return ResponseEntity.ok(content);
    }

    // POST /api/admin/contents/{id}/flag - Flag/suspend content upload
    @PostMapping("/contents/{id}/flag")
    public ResponseEntity<ContentResponse> flagContent(@PathVariable("id") Long id) {
        ContentResponse content = contentService.updateApprovalStatus(id, ApprovalStatus.FLAGGED);
        return ResponseEntity.ok(content);
    }
}
