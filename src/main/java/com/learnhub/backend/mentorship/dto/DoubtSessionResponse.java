package com.learnhub.backend.mentorship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DoubtSessionResponse {

    private Long id;

    @JsonProperty("learner_id")
    private Long learnerId;

    @JsonProperty("creator_id")
    private Long creatorId;

    private String topic;

    @JsonProperty("scheduled_at")
    private LocalDateTime scheduledAt;

    @JsonProperty("duration_minutes")
    private Integer durationMinutes;

    @JsonProperty("session_price")
    private BigDecimal sessionPrice;

    @JsonProperty("booking_status")
    private String bookingStatus;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("jitsi_room_name")
    private String jitsiRoomName;

    @JsonProperty("jitsi_meeting_link")
    private String jitsiMeetingLink;

    public DoubtSessionResponse() {
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

    public String getJitsiMeetingLink() {
        return jitsiMeetingLink;
    }

    public void setJitsiMeetingLink(String jitsiMeetingLink) {
        this.jitsiMeetingLink = jitsiMeetingLink;
    }
}
