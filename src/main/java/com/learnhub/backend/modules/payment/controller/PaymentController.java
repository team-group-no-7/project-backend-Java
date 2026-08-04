package com.learnhub.backend.modules.payment.controller;

import com.learnhub.backend.modules.payment.dto.OrderRequestDto;
import com.learnhub.backend.modules.payment.dto.OrderResponseDto;
import com.learnhub.backend.modules.payment.dto.PaymentVerificationRequestDto;
import com.learnhub.backend.modules.payment.dto.PaymentVerificationResponseDto;
import com.learnhub.backend.modules.payment.entity.Purchase;
import com.learnhub.backend.modules.payment.service.RazorpayService;
import com.learnhub.backend.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PaymentController — REST Controller for Billing & Razorpay Payment Integration.
 * Base URL: /api/payment
 *
 * Implemented in pure Java with explicit constructor dependency injection (No Lombok).
 * Handles order creation, signature verification, and user purchase history.
 */
@RestController
@RequestMapping("/api/payment")
@PreAuthorize("isAuthenticated()")
public class PaymentController {

    private final RazorpayService razorpayService;

    // Explicit constructor dependency injection (No Lombok)
    public PaymentController(RazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    /**
     * POST /api/payment/create-order
     * Initializes a new Razorpay checkout transaction.
     */
    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto orderResponse = razorpayService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Razorpay order created successfully", orderResponse));
    }

    /**
     * POST /api/payment/verify
     * Verifies Razorpay HMAC signature and unlocks content access.
     */
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<PaymentVerificationResponseDto>> verifyPayment(@Valid @RequestBody PaymentVerificationRequestDto request) {
        PaymentVerificationResponseDto response = razorpayService.verifyPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Payment verified successfully", response));
    }

    /**
     * GET /api/payment/purchases/{userId}
     * Returns purchase history DTOs for a specific user.
     */
    @GetMapping("/purchases/{userId}")
    public ResponseEntity<ApiResponse<List<com.learnhub.backend.modules.payment.dto.response.PurchaseResponse>>> getUserPurchases(@PathVariable Long userId) {
        List<Purchase> purchases = razorpayService.getPurchasesForUser(userId);
        List<com.learnhub.backend.modules.payment.dto.response.PurchaseResponse> responseList = purchases.stream()
                .map(p -> new com.learnhub.backend.modules.payment.dto.response.PurchaseResponse(
                        p.getId(),
                        p.getContent() != null ? p.getContent().getId() : null,
                        p.getContent() != null ? p.getContent().getTitle() : "Learning Resource",
                        p.getContent() != null && p.getContent().getCategory() != null ? p.getContent().getCategory().getName() : "General",
                        p.getAmountPaid(),
                        p.getPaymentStatus() != null ? p.getPaymentStatus() : "SUCCESS",
                        p.getPurchasedAt()
                ))
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("User purchases retrieved successfully", responseList));
    }
}
