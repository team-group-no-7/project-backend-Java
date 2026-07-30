# Learner Module Testing Guide

This guide contains step-by-step Postman request/response JSON examples for testing the Learner endpoints.

## 1. Get Learner Dashboard
**Endpoint:** `GET /api/learners/dashboard/{userId}`

**Request:**
```http
GET /api/learners/dashboard/1
Accept: application/json
```

**Response (200 OK):**
```json
{
  "activeResources": 2,
  "completedResources": 0,
  "totalInvestment": 49.99,
  "continueLearning": [
    {
      "contentId": 101,
      "title": "Java Fundamentals",
      "type": "COURSE",
      "category": "Programming",
      "fileUrl": "http://example.com/java-fund.pdf"
    }
  ]
}
```

## 2. Get Learner Profile
**Endpoint:** `GET /api/learners/profile/{userId}`

**Request:**
```http
GET /api/learners/profile/1
Accept: application/json
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane@example.com",
  "role": "LEARNER",
  "avatarUrl": "http://example.com/avatar.jpg",
  "headline": "Enthusiastic Learner",
  "location": "New York"
}
```

## 3. Update Learner Profile
**Endpoint:** `PUT /api/learners/profile/{userId}`

**Request:**
```http
PUT /api/learners/profile/1
Content-Type: application/json

{
  "name": "Jane Doe Updated",
  "headline": "Senior Learner",
  "location": "Boston",
  "avatarUrl": "http://example.com/avatar_new.jpg"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Jane Doe Updated",
  "email": "jane@example.com",
  "role": "LEARNER",
  "avatarUrl": "http://example.com/avatar_new.jpg",
  "headline": "Senior Learner",
  "location": "Boston"
}
```

## 4. Get Purchase History
**Endpoint:** `GET /api/purchases/history`

*(Note: Assumes endpoint is implemented in billing module, test as per billing specs.)*
