package com.learnhub.backend.mentorship.entity;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.mentorship.enums.BookingStatus;
import com.learnhub.backend.mentorship.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "doubt_sessions")
public class DoubtSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "learner_id")
    private User learner;

    @Column(name = "learner_id", insertable = false, updatable = false)
    private Long learnerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "creator_id", insertable = false, updatable = false)
    private Long creatorId;

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

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "jitsi_room_name", unique = true)
    private String jitsiRoomName;

    public DoubtSession() {
    }

    public DoubtSession(Long id, User learner, User creator, String topic, LocalDateTime scheduledAt, Integer durationMinutes, BigDecimal sessionPrice, BookingStatus bookingStatus, PaymentStatus paymentStatus, String transactionId, String jitsiRoomName) {
        this.id = id;
        this.learner = learner;
        if (learner != null) {
            this.learnerId = learner.getId();
        }
        this.creator = creator;
        if (creator != null) {
            this.creatorId = creator.getId();
        }
        this.topic = topic;
        this.scheduledAt = scheduledAt;
        this.durationMinutes = durationMinutes;
        this.sessionPrice = sessionPrice;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.jitsiRoomName = jitsiRoomName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getLearner() { return learner; }
    public void setLearner(User learner) { 
        this.learner = learner; 
        if (learner != null) {
            this.learnerId = learner.getId();
        }
    }

    public Long getLearnerId() { return learnerId; }
    public void setLearnerId(Long learnerId) { this.learnerId = learnerId; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { 
        this.creator = creator; 
        if (creator != null) {
            this.creatorId = creator.getId();
        }
    }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public BigDecimal getSessionPrice() { return sessionPrice; }
    public void setSessionPrice(BigDecimal sessionPrice) { this.sessionPrice = sessionPrice; }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getJitsiRoomName() { return jitsiRoomName; }
    public void setJitsiRoomName(String jitsiRoomName) { this.jitsiRoomName = jitsiRoomName; }
}
