package com.learnhub.backend.mentorship.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DoubtSession Entity — Maps to the DOUBT_SESSIONS table in database.
 * Represents a scheduled 1-on-1 mentorship call between a Learner and a Creator.
 */
@Entity
@Table(name = "doubt_sessions")
public class DoubtSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "learner_id", nullable = false)
    private Long learnerId;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(nullable = false)
    private String topic;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "duration_minutes")
    private Integer durationMinutes = 30;

    @Column(name = "session_price", nullable = false)
    private BigDecimal sessionPrice = BigDecimal.ZERO;

    @Column(name = "booking_status")
    private String bookingStatus = "PENDING"; // PENDING, CONFIRMED, COMPLETED, CANCELLED

    @Column(name = "payment_status")
    private String paymentStatus = "UNPAID"; // UNPAID, PAID

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "jitsi_room_name", unique = true)
    private String jitsiRoomName;

    public DoubtSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getJitsiRoomName() {
        return jitsiRoomName;
    }

    public void setJitsiRoomName(String jitsiRoomName) {
        this.jitsiRoomName = jitsiRoomName;
    }
}
