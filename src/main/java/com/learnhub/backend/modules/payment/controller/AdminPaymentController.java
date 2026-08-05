package com.learnhub.backend.modules.payment.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.payment.entity.Purchase;
import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AdminPaymentController — Handles administrative payment and revenue oversight endpoints.
 * Provides live transaction lists and administrative refund controls persisting to database.
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPaymentController {

    private final PurchaseRepository purchaseRepository;

    public AdminPaymentController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("/payments/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Admin Payment Module is Active", "OK"));
    }

    @GetMapping({"/transactions", "/payments/all"})
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllTransactions() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<Map<String, Object>> responseList = purchases.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", p.getId());
                    map.put("purchaseId", p.getId());
                    map.put("transactionId", p.getTransactionId() != null ? p.getTransactionId() : "TXN-" + p.getId());
                    map.put("contentId", p.getContentId());
                    map.put("title", p.getContent() != null ? p.getContent().getTitle() : "Learning Resource");
                    map.put("category", p.getContent() != null && p.getContent().getCategory() != null ? p.getContent().getCategory().getName() : "General");
                    map.put("amount", p.getAmountPaid());
                    map.put("amountPaid", p.getAmountPaid());
                    map.put("status", p.getPaymentStatus() != null ? p.getPaymentStatus() : "SUCCESS");
                    map.put("paymentStatus", p.getPaymentStatus() != null ? p.getPaymentStatus() : "SUCCESS");
                    map.put("user", p.getUser() != null ? p.getUser().getName() : "Learner #" + p.getUserId());
                    map.put("userEmail", p.getUser() != null ? p.getUser().getEmail() : "learner@learnhub.com");
                    map.put("date", p.getPurchasedAt() != null ? p.getPurchasedAt().toString() : LocalDateTime.now().toString());
                    map.put("purchasedAt", p.getPurchasedAt() != null ? p.getPurchasedAt().toString() : null);
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("All transactions retrieved successfully from database", responseList));
    }

    @PostMapping("/transactions/{id}/refund")
    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> refundTransaction(@PathVariable("id") Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction purchase not found with id: " + id));

        purchase.setPaymentStatus("REFUNDED");
        Purchase saved = purchaseRepository.save(purchase);

        Map<String, Object> res = new HashMap<>();
        res.put("id", saved.getId());
        res.put("status", saved.getPaymentStatus());
        res.put("amountRefunded", saved.getAmountPaid());
        res.put("message", "Transaction #" + id + " marked as REFUNDED in PostgreSQL database.");

        return ResponseEntity.ok(ApiResponse.success("Administrative refund processed successfully", res));
    }
}
