package com.learnhub.backend.billing.entity;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.catalog.entity.Content;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Purchase Entity — Maps to the PURCHASES table in database.
 * Implemented in pure Java without Lombok annotations.
 */
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(name = "amount_paid")
    private BigDecimal amountPaid;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;

    // Default Constructor (Required by JPA)
    public Purchase() {
    }

    // Parameterized Constructor
    public Purchase(Long id, User user, Content content, BigDecimal amountPaid, String paymentStatus, String transactionId, LocalDateTime purchasedAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.purchasedAt = purchasedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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
                ", amountPaid=" + amountPaid +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}