package com.learnhub.backend.mentorship.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SessionResponse DTO — Pure Java DTO without Lombok annotations.
 */
public class SessionResponse {

    private Long id;
    private String topic;
    private LocalDateTime scheduledAt;
    private Integer durationMinutes;
    private BigDecimal sessionPrice;
    private String bookingStatus;
    private String paymentStatus;
    private String creatorName;
    private String jitsiRoomName;

    // Default Constructor
    public SessionResponse() {
    }

    // Parameterized Constructor
    public SessionResponse(Long id, String topic, LocalDateTime scheduledAt, Integer durationMinutes, BigDecimal sessionPrice, String bookingStatus, String paymentStatus, String creatorName, String jitsiRoomName) {
        this.id = id;
        this.topic = topic;
        this.scheduledAt = scheduledAt;
        this.durationMinutes = durationMinutes;
        this.sessionPrice = sessionPrice;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.creatorName = creatorName;
        this.jitsiRoomName = jitsiRoomName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(BigDecimal sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getJitsiRoomName() {
        return jitsiRoomName;
    }

    public void setJitsiRoomName(String jitsiRoomName) {
        this.jitsiRoomName = jitsiRoomName;
    }

    @Override
    public String toString() {
        return "SessionResponse{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}