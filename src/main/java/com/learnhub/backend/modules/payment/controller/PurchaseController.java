package com.learnhub.backend.modules.payment.controller;

import com.learnhub.backend.modules.payment.dto.response.PurchaseResponse;
import com.learnhub.backend.modules.user.dto.response.LibraryResponse;
import com.learnhub.backend.modules.payment.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PurchaseController — REST Controller for Learner Purchase History and Library Resources.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    // Explicit Constructor Injection
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{userId}")
    public List<PurchaseResponse> history(@PathVariable Long userId) {
        return purchaseService.getPurchaseHistory(userId);
    }

    @GetMapping("/library/{userId}")
    public List<LibraryResponse> library(@PathVariable Long userId) {
        return purchaseService.getMyLibrary(userId);
    }
}