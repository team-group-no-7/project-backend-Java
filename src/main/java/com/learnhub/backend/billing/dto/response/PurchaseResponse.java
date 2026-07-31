package com.learnhub.backend.billing.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PurchaseResponse DTO — Pure Java DTO without Lombok annotations.
 */
public class PurchaseResponse {

    private Long purchaseId;
    private Long contentId;
    private String title;
    private String category;
    private BigDecimal amountPaid;
    private String paymentStatus;
    private LocalDateTime purchasedAt;

    // Default Constructor
    public PurchaseResponse() {
    }

    // Parameterized Constructor
    public PurchaseResponse(Long purchaseId, Long contentId, String title, String category, BigDecimal amountPaid, String paymentStatus, LocalDateTime purchasedAt) {
        this.purchaseId = purchaseId;
        this.contentId = contentId;
        this.title = title;
        this.category = category;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.purchasedAt = purchasedAt;
    }

    // Getters and Setters
    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    @Override
    public String toString() {
        return "PurchaseResponse{" +
                "purchaseId=" + purchaseId +
                ", contentId=" + contentId +
                ", title='" + title + '\'' +
                ", amountPaid=" + amountPaid +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}