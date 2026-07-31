package com.learnhub.backend.billing.entity;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.catalog.entity.Content;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "content_id")
    private Long contentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    private Content content;

    @Column(name = "amount_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "payment_status")
    private String paymentStatus = "PENDING"; // PENDING, SUCCESS, FAILED

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;

    public Purchase() {
    }

    public Purchase(Long id, User user, Content content, BigDecimal amountPaid, String paymentStatus, String transactionId, LocalDateTime purchasedAt) {
        this.id = id;
        this.user = user;
        if (user != null) {
            this.userId = user.getId();
        }
        this.content = content;
        if (content != null) {
            this.contentId = content.getId();
        }
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.purchasedAt = purchasedAt;
    }

    public Purchase(Long id, Long userId, Long contentId, BigDecimal amountPaid, String paymentStatus, String transactionId, LocalDateTime purchasedAt) {
        this.id = id;
        this.userId = userId;
        this.contentId = contentId;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.purchasedAt = purchasedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { 
        this.user = user; 
        if (user != null) {
            this.userId = user.getId();
        }
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Content getContent() { return content; }
    public void setContent(Content content) { 
        this.content = content; 
        if (content != null) {
            this.contentId = content.getId();
        }
    }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getPurchasedAt() { return purchasedAt; }
    public void setPurchasedAt(LocalDateTime purchasedAt) { this.purchasedAt = purchasedAt; }
}
