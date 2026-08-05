package com.learnhub.backend.modules.mentorship.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminMentorshipController — Handles administrative mentorship session oversight endpoints.
 */
@RestController
@RequestMapping("/api/admin/mentorship")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMentorshipController {

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Admin Mentorship Module is Active", "OK"));
    }
}
