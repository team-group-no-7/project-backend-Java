package com.learnhub.backend.billing.service;

import com.learnhub.backend.billing.dto.OrderRequestDto;
import com.learnhub.backend.billing.dto.OrderResponseDto;
import com.learnhub.backend.billing.dto.PaymentVerificationRequestDto;
import com.learnhub.backend.billing.dto.PaymentVerificationResponseDto;
import com.learnhub.backend.billing.entity.Purchase;

import java.util.List;

/**
 * RazorpayService — Interface for Razorpay order generation and payment verification.
 */
public interface RazorpayService {

    OrderResponseDto createOrder(OrderRequestDto request);

    PaymentVerificationResponseDto verifyPayment(PaymentVerificationRequestDto request);

    List<Purchase> getPurchasesForUser(Long userId);
}
