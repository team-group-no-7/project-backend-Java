package com.learnhub.backend.billing.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Purchase Entity — Maps to the PURCHASES table in PostgreSQL.
 * Tracks user digital content purchases and Razorpay payment transactions.
 *
 * Implemented in pure Java without Lombok annotations.
 */
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "amount_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "payment_status")
    private String paymentStatus = "PENDING"; // PENDING, SUCCESS, FAILED

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "purchased_at", insertable = false, updatable = false)
    private LocalDateTime purchasedAt;

    public Purchase() {
    }

    public Purchase(Long id, Long userId, Long contentId, BigDecimal amountPaid,
                    String paymentStatus, String transactionId, LocalDateTime purchasedAt) {
        this.id = id;
        this.userId = userId;
        this.contentId = contentId;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.purchasedAt = purchasedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", amountPaid=" + amountPaid +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
