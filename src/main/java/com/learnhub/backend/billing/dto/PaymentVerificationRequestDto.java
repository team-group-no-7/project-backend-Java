package com.learnhub.backend.billing.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * PaymentVerificationRequestDto — Payload sent by frontend to verify Razorpay transaction signature.
 * Implemented in pure Java without Lombok.
 */
public class PaymentVerificationRequestDto {

    @NotBlank(message = "Razorpay Order ID is required")
    private String razorpayOrderId;

    @NotBlank(message = "Razorpay Payment ID is required")
    private String razorpayPaymentId;

    private String razorpaySignature;

    private Long userId;

    private Long contentId;

    public PaymentVerificationRequestDto() {
    }

    public PaymentVerificationRequestDto(String razorpayOrderId, String razorpayPaymentId,
                                        String razorpaySignature, Long userId, Long contentId) {
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.userId = userId;
        this.contentId = contentId;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
