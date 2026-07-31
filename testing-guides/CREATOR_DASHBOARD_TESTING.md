# 📊 LearnHub Creator Dashboard & Catalog Module (`com.learnhub.backend.catalog`)

> **Developer/Owner:** Yashwant  
> **Branch:** `feature/creator-dashboard`  
> **Tech Stack:** Spring Boot 4.1.0, Spring Data JPA, PostgreSQL, JWT Authentication  

Welcome to the **Creator Dashboard Analytics Testing Guide**! This document provides a complete walkthrough for testing the creator dashboard analytics APIs.

---

## 📌 Architecture Summary

- **Module Purpose:** Aggregates analytics for creators (total resources uploaded, total enrolled learners across all resources, and total earnings).
- **Authentication:** Endpoint requires a valid JWT Bearer Token passed in the HTTP request header:
  `Authorization: Bearer <your-jwt-token>`
- **JPQL Query Aggregation:**
  - `totalResources`: `countByCreatorId(creatorId)`
  - `totalLearners`: `SUM(learnersCount)` for creator's resources
  - `totalEarnings`: `SUM(price * learnersCount)` for creator's resources

---

## ⚙️ Prerequisites Before Testing

1. **Database & Backend Running**:
   Ensure PostgreSQL is running and start the backend:
   ```powershell
   .\mvnw spring-boot:run
   ```
   *The server will run at `http://localhost:8080`.*

2. **Obtain a JWT Token**:
   Register or login via Auth endpoints to get a JWT token:
   - `POST http://localhost:8080/api/auth/login`
   - Copy the `"token"` string.

---

## 🧪 Step-by-Step API Testing Workflow

---

### Step 1: Check Creator Module Health Status
Verify that the Creator Dashboard module is active.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/creators/status`
- **Headers:** None required
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Creator Dashboard Module is Active",
    "data": "OK"
  }
  ```

---

### Step 2: Fetch Creator Dashboard Analytics
Fetch metrics for a creator by ID (e.g. Creator ID `1`).

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/creators/1/dashboard-stats`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Creator dashboard statistics retrieved successfully",
    "data": {
      "totalResources": 0,
      "totalLearners": 0,
      "totalEarnings": 0.0
    }
  }
  ```

---

### Step 3: Test Non-Existent Creator (404 Error Check)
Verify that requesting stats for a non-existent creator ID returns `404 Not Found`.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/creators/99999/dashboard-stats`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `404 Not Found`
- **Expected Response:**
  ```json
  {
    "success": false,
    "message": "Creator not found with id: 99999",
    "data": null
  }
  ```

---

## 📋 Endpoint Summary Table

| Method | Endpoint | Access | Description |
|:---|:---|:---|:---|
| `GET` | `/api/creators/status` | Public | Health check for Creator Dashboard module |
| `GET` | `/api/creators/{creatorId}/dashboard-stats` | JWT Protected | Returns aggregated totalResources, totalLearners, totalEarnings |

---

## 🤝 Support
If you encounter any issues while testing dashboard endpoints, contact **Yashwant** on the team channel!
