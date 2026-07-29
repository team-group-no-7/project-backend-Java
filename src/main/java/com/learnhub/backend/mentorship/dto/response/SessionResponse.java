package com.learnhub.backend.mentorship.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}