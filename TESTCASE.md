# 🧪 LearnHub Backend Test Cases & Verification Guide

> **Branch**: `landing` (`feature/landing-page` & `feature/payment-razorpay`)  
> **Author**: Sakshi  
> **Stack**: Spring Boot 4.1.0 · Java 17 · PostgreSQL · Razorpay API · JWT  

---

## 📌 Overview
This document provides a **comprehensive suite of test cases** for any developer pulling the `landing` / `feature/landing-page` branch. It covers positive paths, negative paths, boundary conditions, and exact HTTP request/response JSON payloads for testing via **Postman**, **cURL**, or **JUnit**.

---

## 📑 Test Suite Index
1. [Module 1: Landing Page & Featured Content APIs](#-module-1-landing-page--featured-content-apis)
2. [Module 2: Resource Details API](#-module-2-resource-details-api)
3. [Module 3: Billing & Razorpay Payment Integration APIs](#-module-3-billing--razorpay-payment-integration-apis)
4. [Module 4: Authentication & Security APIs](#-module-4-authentication--security-apis)
5. [Module 5: Edge Cases & Error Responses](#-module-5-edge-cases--error-responses)

---

## 🌟 Module 1: Landing Page & Featured Content APIs

### **TC-LP-001: Fetch Complete Aggregated Landing Page Data**
- **Endpoint**: `GET /api/public/landing`
- **Description**: Verifies fetching all home page sections (featured, trending, categories, top creators, platform metrics) in a single API call.
- **Headers**: None (Public)
- **Expected Status**: `200 OK`
- **Expected Response**:
```json
{
  "success": true,
  "message": "Landing page data fetched successfully",
  "data": {
    "featuredContents": [
      {
        "id": 10,
        "title": "Complete Java Spring Boot Monolith",
        "price": 499.0,
        "type": "Notes & Code",
        "rating": 4.85,
        "categoryName": "Java",
        "creatorName": "Rohan Verma"
      }
    ],
    "trendingContents": [],
    "categories": [
      { "id": 1, "name": "Java", "resourceCount": 2 },
      { "id": 2, "name": "DSA", "resourceCount": 1 }
    ],
    "topCreators": [
      {
        "id": 202,
        "name": "Rohan Verma",
        "email": "rohan.verma@learnhub.com",
        "publishedResourcesCount": 2
      }
    ],
    "totalResourcesCount": 2,
    "totalLearnersCount": 1,
    "totalCreatorsCount": 1
  }
}
```

---

### **TC-LP-002: Fetch Featured Contents**
- **Endpoint**: `GET /api/public/featured`
- **Expected Status**: `200 OK`
- **Verification**: Returns list of top-rated / featured study resources.

---

### **TC-LP-003: Fetch Categories Taxonomy**
- **Endpoint**: `GET /api/public/categories`
- **Expected Status**: `200 OK`
- **Verification**: Returns categories sorted alphabetically with `resourceCount`.

---

### **TC-LP-004: Fetch Top Creators**
- **Endpoint**: `GET /api/public/top-creators`
- **Expected Status**: `200 OK`
- **Verification**: Returns creator profiles, headlines, and published resource counts.

---

## 📖 Module 2: Resource Details API

### **TC-RD-001: Fetch Valid Resource Details by ID**
- **Endpoint**: `GET /api/public/resource/10` (or `GET /api/public/contents/10`)
- **Description**: Verifies fetching single resource page details consumable by `ResourceDetailPage.jsx`.
- **Expected Status**: `200 OK`
- **Expected Response**:
```json
{
  "success": true,
  "message": "Resource details fetched successfully",
  "data": {
    "id": 10,
    "title": "Complete Java Spring Boot Monolith",
    "description": "Learn microservices, transactions, and JPA.",
    "previewText": "Chapter 1: Configuration, Chapter 2: Security",
    "price": 499.0,
    "type": "Notes & Code",
    "level": "Intermediate",
    "tags": ["Java", "Spring Boot", "Backend"],
    "rating": 4.85,
    "reviewsCount": 2,
    "learnersCount": 0,
    "categoryId": 1,
    "categoryName": "Java",
    "creatorId": 202,
    "creatorName": "Rohan Verma",
    "creatorAvatar": "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
    "reviews": [
      {
        "id": 1,
        "studentName": "Aarav Sharma",
        "rating": 5,
        "comment": "Exceptionally clear explanation of Spring Boot architecture and PostgreSQL indexing!",
        "date": "2026-07-15"
      }
    ]
  }
}
```

---

### **TC-RD-002: Fetch Non-Existent Resource ID (404 Not Found)**
- **Endpoint**: `GET /api/public/resource/99999`
- **Expected Status**: `404 Not Found`
- **Expected Response**:
```json
{
  "success": false,
  "message": "Resource not found with id: 99999",
  "data": null
}
```

---

## 💳 Module 3: Billing & Razorpay Payment Integration APIs

### **TC-PAY-001: Create Razorpay Order (Valid Payload)**
- **Endpoint**: `POST /api/payment/create-order`
- **Request Body**:
```json
{
  "contentId": 10,
  "userId": 101,
  "amount": 499.00
}
```
- **Expected Status**: `201 Created`
- **Expected Response**:
```json
{
  "success": true,
  "message": "Razorpay order created successfully",
  "data": {
    "orderId": "order_lh_a1b2c3d4",
    "transactionId": "txn_lh_a1b2c3d4",
    "amount": 499.00,
    "currency": "INR",
    "razorpayKeyId": "rzp_test_learnhub123",
    "contentTitle": "Complete Java Spring Boot Monolith",
    "status": "CREATED"
  }
}
```

---

### **TC-PAY-002: Create Razorpay Order Without Content ID (Validation Error)**
- **Endpoint**: `POST /api/payment/create-order`
- **Request Body**:
```json
{
  "amount": 499.00
}
```
- **Expected Status**: `400 Bad Request`
- **Verification**: Bean validation fails on `@NotNull` for `contentId`.

---

### **TC-PAY-003: Verify Razorpay Payment Signature (Successful Verification)**
- **Endpoint**: `POST /api/payment/verify`
- **Request Body**:
```json
{
  "razorpayOrderId": "order_lh_a1b2c3d4",
  "razorpayPaymentId": "pay_test_987654",
  "razorpaySignature": "mock_signature_valid",
  "userId": 101,
  "contentId": 10
}
```
- **Expected Status**: `200 OK`
- **Expected Response**:
```json
{
  "success": true,
  "message": "Payment verified successfully",
  "data": {
    "status": "SUCCESS",
    "message": "Payment verified successfully. Content access unlocked!",
    "purchaseId": 1,
    "transactionId": "pay_test_987654",
    "contentId": 10
  }
}
```

---

### **TC-PAY-004: Get Purchase History for User**
- **Endpoint**: `GET /api/payment/purchases/101`
- **Expected Status**: `200 OK`
- **Verification**: Returns list of all purchase ledger entries for user ID `101` with status `SUCCESS`.

---

## 🔐 Module 4: Authentication & Security APIs

### **TC-AUTH-001: User Login**
- **Endpoint**: `POST /api/auth/login`
- **Request Body**:
```json
{
  "email": "arjun.mehta@learnhub.com",
  "password": "pass123"
}
```
- **Expected Status**: `200 OK`
- **Expected Response**:
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "sample-refresh-token-arjun-101",
    "email": "arjun.mehta@learnhub.com",
    "role": "LEARNER"
  }
}
```

---

## ⚠️ Module 5: Edge Cases & Error Responses

| Test Case ID | Scenario | Input / Action | Expected HTTP Status | Expected Outcome |
| :--- | :--- | :--- | :--- | :--- |
| **TC-EDGE-001** | Invalid URL Path | `GET /api/public/nonexistent` | `404 Not Found` | Handled by Spring WebMVC default error router. |
| **TC-EDGE-002** | Invalid JSON Payload | `POST /api/payment/create-order` with malformed JSON | `400 Bad Request` | Handled by `GlobalExceptionHandler`. |
| **TC-EDGE-003** | Free Resource Checkout | `POST /api/payment/create-order` for resource with price `0.00` | `201 Created` | Order generated with `0.00` amount. |
| **TC-EDGE-004** | Invalid Resource ID Checkout | `POST /api/payment/create-order` with `contentId: 99999` | `404 Not Found` | `ResourceNotFoundException` thrown. |

---

## 🛠️ cURL Quick Command Examples

```bash
# 1. Test Aggregated Landing Page
curl -X GET http://localhost:8080/api/public/landing

# 2. Test Resource Details
curl -X GET http://localhost:8080/api/public/resource/10

# 3. Test Create Razorpay Order
curl -X POST http://localhost:8080/api/payment/create-order \
  -H "Content-Type: application/json" \
  -d '{"contentId": 10, "userId": 101, "amount": 499.00}'

# 4. Test Verify Payment
curl -X POST http://localhost:8080/api/payment/verify \
  -H "Content-Type: application/json" \
  -d '{"razorpayOrderId": "order_lh_123", "razorpayPaymentId": "pay_456", "userId": 101, "contentId": 10}'
```
