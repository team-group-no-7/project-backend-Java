package com.learnhub.backend.modules.payment.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.payment.dto.response.PurchaseResponse;
import com.learnhub.backend.modules.user.dto.response.LibraryResponse;
import com.learnhub.backend.modules.payment.service.PurchaseService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PurchaseController — REST Controller for Learner Purchase History and Library Resources.
 * Refactored with class-level @PreAuthorize and automatic JWT identity resolution.
 */
@RestController
@RequestMapping("/api/purchases")
@PreAuthorize("isAuthenticated()")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final UserRepository userRepository;

    public PurchaseController(PurchaseService purchaseService, UserRepository userRepository) {
        this.purchaseService = purchaseService;
        this.userRepository = userRepository;
    }

    /** GET /api/purchases/my-history or GET /api/purchases/{userId} */
    @GetMapping({"", "/my-history", "/{userId}"})
    public ResponseEntity<ApiResponse<List<PurchaseResponse>>> history(@PathVariable(required = false) Long userId) {
        Long resolvedUserId = resolveUserId(userId);
        List<PurchaseResponse> history = purchaseService.getPurchaseHistory(resolvedUserId);
        return ResponseEntity.ok(ApiResponse.success("Purchase history retrieved successfully", history));
    }

    /** GET /api/purchases/library or GET /api/purchases/library/{userId} */
    @GetMapping({"/library", "/library/{userId}"})
    public ResponseEntity<ApiResponse<List<LibraryResponse>>> library(@PathVariable(required = false) Long userId) {
        Long resolvedUserId = resolveUserId(userId);
        List<LibraryResponse> library = purchaseService.getMyLibrary(resolvedUserId);
        return ResponseEntity.ok(ApiResponse.success("My library retrieved successfully", library));
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