package com.learnhub.backend.modules.payment.service;

import com.learnhub.backend.modules.payment.dto.OrderRequestDto;
import com.learnhub.backend.modules.payment.dto.OrderResponseDto;
import com.learnhub.backend.modules.payment.dto.PaymentVerificationRequestDto;
import com.learnhub.backend.modules.payment.dto.PaymentVerificationResponseDto;
import com.learnhub.backend.modules.payment.entity.Purchase;

import java.util.List;

/**
 * RazorpayService — Interface for Razorpay order generation and payment verification.
 */
public interface RazorpayService {

    OrderResponseDto createOrder(OrderRequestDto request);

    PaymentVerificationResponseDto verifyPayment(PaymentVerificationRequestDto request);

    List<Purchase> getPurchasesForUser(Long userId);
}
