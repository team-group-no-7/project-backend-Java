package com.learnhub.backend.mentorship.entity;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.mentorship.enums.BookingStatus;
import com.learnhub.backend.mentorship.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DoubtSession Entity — Maps to DOUBT_SESSIONS table.
 * Implemented in pure Java without Lombok annotations.
 */
@Entity
@Table(name = "doubt_sessions")
public class DoubtSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id")
    private User learner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    private String topic;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "session_price")
    private BigDecimal sessionPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "jitsi_room_name")
    private String jitsiRoomName;

    // Default Constructor (Required by JPA)
    public DoubtSession() {
    }

    // Parameterized Constructor
    public DoubtSession(Long id, User learner, User creator, String topic, LocalDateTime scheduledAt, Integer durationMinutes, BigDecimal sessionPrice, BookingStatus bookingStatus, PaymentStatus paymentStatus, String transactionId, String jitsiRoomName) {
        this.id = id;
        this.learner = learner;
        this.creator = creator;
        this.topic = topic;
        this.scheduledAt = scheduledAt;
        this.durationMinutes = durationMinutes;
        this.sessionPrice = sessionPrice;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.jitsiRoomName = jitsiRoomName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getLearner() {
        return learner;
    }

    public void setLearner(User learner) {
        this.learner = learner;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
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

    @Override
    public String toString() {
        return "DoubtSession{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}