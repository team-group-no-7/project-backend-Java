package com.learnhub.backend.billing.dto;

/**
 * PaymentVerificationResponseDto — Confirmation response returned after verifying payment signature.
 * Implemented in pure Java without Lombok.
 */
public class PaymentVerificationResponseDto {

    private String status;
    private String message;
    private Long purchaseId;
    private String transactionId;
    private Long contentId;

    public PaymentVerificationResponseDto() {
    }

    public PaymentVerificationResponseDto(String status, String message, Long purchaseId, String transactionId, Long contentId) {
        this.status = status;
        this.message = message;
        this.purchaseId = purchaseId;
        this.transactionId = transactionId;
        this.contentId = contentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
