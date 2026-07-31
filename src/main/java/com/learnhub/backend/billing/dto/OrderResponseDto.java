package com.learnhub.backend.billing.dto;

import java.math.BigDecimal;

/**
 * OrderResponseDto — Response payload returned to frontend after creating Razorpay order.
 * Implemented in pure Java without Lombok.
 */
public class OrderResponseDto {

    private String orderId;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private String razorpayKeyId;
    private String contentTitle;
    private String status;

    public OrderResponseDto() {
    }

    public OrderResponseDto(String orderId, String transactionId, BigDecimal amount, String currency,
                            String razorpayKeyId, String contentTitle, String status) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.razorpayKeyId = razorpayKeyId;
        this.contentTitle = contentTitle;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRazorpayKeyId() {
        return razorpayKeyId;
    }

    public void setRazorpayKeyId(String razorpayKeyId) {
        this.razorpayKeyId = razorpayKeyId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
