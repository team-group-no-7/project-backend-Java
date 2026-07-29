package com.learnhub.backend.billing.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponse {

    private Long purchaseId;

    private Long contentId;

    private String title;

    private String category;

    private BigDecimal amountPaid;

    private String paymentStatus;

    private LocalDateTime purchasedAt;

}