package com.learnhub.backend.billing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BillingController — Placeholder endpoint for Checkout & Billing Module.
 * Dedicated package area for Team Member working on Payments/Razorpay.
 */
@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @GetMapping("/status")
    public String getStatus() {
        return "Billing & Purchase Module is Active";
    }
}
