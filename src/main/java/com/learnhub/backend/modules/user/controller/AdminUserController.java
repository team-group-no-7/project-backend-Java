package com.learnhub.backend.modules.user.controller;

import com.learnhub.backend.modules.user.dto.AdminUserResponse;
import com.learnhub.backend.modules.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * AdminUserController — Handles administrative user management tasks (listing, freezing/unfreezing users).
 * Preserves exact path mapping (/api/admin/users) and DTO format for frontend compatibility.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/admin/users - Search and list users
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> searchUsers(
            @RequestParam(value = "search", required = false) String search) {
        List<AdminUserResponse> users = userService.searchUsers(search);
        return ResponseEntity.ok(users);
    }

    // POST /api/admin/users/{id}/freeze & PATCH /api/admin/users/{id}/status - Toggle user freeze state (Active <-> Frozen)
    @RequestMapping(value = {"/users/{id}/freeze", "/users/{id}/status"}, method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<AdminUserResponse> toggleUserFreeze(@PathVariable("id") Long id) {
        AdminUserResponse updatedUser = userService.toggleUserFreeze(id);
        return ResponseEntity.ok(updatedUser);
    }
}
