package com.learnhub.backend.admin.controller;

import com.learnhub.backend.admin.dto.AdminUserResponse;
import com.learnhub.backend.admin.dto.PlatformStatsResponse;
import com.learnhub.backend.admin.service.AdminService;
import com.learnhub.backend.catalog.dto.ContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * AdminController — Handles administrative management tasks (freezing users, approving resources, and platform metrics).
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // GET /api/admin/stats & /api/admin/analytics - Retrieve high-level platform statistics
    @GetMapping({"/stats", "/analytics"})
    public ResponseEntity<PlatformStatsResponse> getPlatformStats() {
        PlatformStatsResponse stats = adminService.getPlatformStats();
        return ResponseEntity.ok(stats);
    }

    // GET /api/admin/users - Search and list users
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> searchUsers(
            @RequestParam(value = "search", required = false) String search) {
        List<AdminUserResponse> users = adminService.searchUsers(search);
        return ResponseEntity.ok(users);
    }

    // POST /api/admin/users/{id}/freeze & PATCH /api/admin/users/{id}/status - Toggle user freeze state (Active <-> Frozen)
    @RequestMapping(value = {"/users/{id}/freeze", "/users/{id}/status"}, method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<AdminUserResponse> toggleUserFreeze(@PathVariable("id") Long id) {
        AdminUserResponse updatedUser = adminService.toggleUserFreeze(id);
        return ResponseEntity.ok(updatedUser);
    }

    // GET /api/admin/contents - List all contents for moderation
    @GetMapping("/contents")
    public ResponseEntity<List<ContentResponse>> getAllContents() {
        List<ContentResponse> contents = adminService.getAllContents();
        return ResponseEntity.ok(contents);
    }

    // POST /api/admin/contents/{id}/approve - Approve content upload
    @PostMapping("/contents/{id}/approve")
    public ResponseEntity<ContentResponse> approveContent(@PathVariable("id") Long id) {
        ContentResponse content = adminService.toggleContentStatus(id, "APPROVED");
        return ResponseEntity.ok(content);
    }

    // POST /api/admin/contents/{id}/flag - Flag/suspend content upload
    @PostMapping("/contents/{id}/flag")
    public ResponseEntity<ContentResponse> flagContent(@PathVariable("id") Long id) {
        ContentResponse content = adminService.toggleContentStatus(id, "FLAGGED");
        return ResponseEntity.ok(content);
    }
}
