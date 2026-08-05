package com.learnhub.backend.modules.discussion.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminDiscussionController — Handles administrative Q&A community moderation endpoints.
 */
@RestController
@RequestMapping("/api/admin/discussions")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDiscussionController {

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Admin Discussion Module is Active", "OK"));
    }
}
