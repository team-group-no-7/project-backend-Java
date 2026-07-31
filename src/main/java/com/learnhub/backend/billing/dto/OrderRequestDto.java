package com.learnhub.backend.billing.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * OrderRequestDto — Request payload for initializing a Razorpay order.
 * Implemented in pure Java without Lombok.
 */
public class OrderRequestDto {

    @NotNull(message = "Content ID is required")
    private Long contentId;

    private Long userId;

    private BigDecimal amount;

    public OrderRequestDto() {
    }

    public OrderRequestDto(Long contentId, Long userId, BigDecimal amount) {
        this.contentId = contentId;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
