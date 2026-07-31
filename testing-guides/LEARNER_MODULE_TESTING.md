# 🎓 LearnHub Learner Workspace Module (`com.learnhub.backend.user`)

> **Developer/Owner:** Riya  
> **Branch:** `learner-dashboard-new`  
> **Tech Stack:** Spring Boot 4.1.0, Spring Data JPA, PostgreSQL, JWT Authentication  

Welcome to the **Learner Workspace Module Testing Guide**! This document details all testing steps for Learner Profile, Learner Dashboard, Course Library, and Mentorship Doubt Sessions.

---

## 📌 Architecture Summary

- **Module Purpose:** Provides APIs for Learners to view/update their profile, check learning metrics (enrolled resources, investment), view purchased content library, and view booked mentorship sessions.
- **Authentication:** All endpoints require a valid JWT Bearer Token:
  `Authorization: Bearer <your-jwt-token>`
- **No Lombok:** Written in 100% pure Java with explicit getters, setters, constructors, and `toString()` methods for CDAC Viva compliance.

---

## 🧪 Step-by-Step API Testing Workflow

---

### Step 1: Get Learner Profile
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/learners/profile/1`
- **Headers:** `Authorization: Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`

---

### Step 2: Update Learner Profile
- **HTTP Method:** `PUT`
- **URL:** `http://localhost:8080/api/learners/profile/1`
- **Headers:** `Authorization: Bearer <your-jwt-token>`, `Content-Type: application/json`
- **Body (JSON):**
  ```json
  {
    "name": "Riya Sharma",
    "headline": "Aspiring Full Stack Java Developer | CDAC Student",
    "location": "Pune, India",
    "avatarUrl": "https://api.dicebear.com/7.x/avataaars/svg?seed=Riya"
  }
  ```
- **Expected Status:** `200 OK`

---

### Step 3: Get Learner Dashboard Metrics
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/learners/dashboard/1`
- **Headers:** `Authorization: Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`

---

### Step 4: Get My Purchased Library
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/purchases/library/1`
- **Headers:** `Authorization: Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`

---

### Step 5: Get Booked Doubt Sessions
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/sessions/1`
- **Headers:** `Authorization: Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
