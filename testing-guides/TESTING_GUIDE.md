# 🧪 LearnHub Backend Test Suite & API Testing Guide

This guide details how to verify and test the implemented modules (Marketplace, Admin Dashboard, and Doubt Sessions) conforming to the API contracts in the LearnHub Backend Development Handbook.

---

## 🔑 Authentication Settings (Local Development)

All secured requests require **HTTP Basic Authentication** headers.
* **Dev Username**: `admin`
* **Dev Password**: `admin123`

---

## 🛍️ 1. Marketplace & Catalog Module (`/api/contents` & `/api/categories`)

### 1.1 Get All Categories
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/categories`
* **Auth**: Public (No authentication required)
* **Expected Response**: `200 OK`
  ```json
  [
    { "id": 1, "name": "Java", "resourceCount": 2 },
    { "id": 2, "name": "DSA", "resourceCount": 1 }
  ]
  ```

### 1.2 Browse and Search Content Catalog
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/contents?search=Monolith&category=Java`
* **Auth**: Public (No authentication required)
* **Expected Response**: `200 OK` (list of matching approved study guides)

### 1.3 Get Featured Contents
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/contents/featured`
* **Auth**: Public
* **Expected Response**: `200 OK` (list of featured contents for landing page)

### 1.4 Get Content Details by ID
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/contents/10`
* **Auth**: Public
* **Expected Response**: `200 OK` with full book metadata details.

### 1.5 Create/Publish New Content (Creator)
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/contents`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Headers**: `Content-Type: application/json`
* **Request Body**:
  ```json
  {
    "title": "Mastering Advanced Algorithms",
    "description": "Deep dive into Graph Theory and Dynamic Programming.",
    "preview_text": "Chapter 1: Graphs, Chapter 2: DP",
    "content_body": "Full course materials...",
    "file_url": "https://s3.amazonaws.com/learnhub/files/dsa-notes.pdf",
    "price": 350.00,
    "type": "Notes & Code",
    "level": "Advanced",
    "tags": "DSA,Algorithms,Java",
    "category_name": "DSA",
    "creator_id": 202
  }
  ```
* **Expected Response**: `201 Created`

### 1.6 Delete Content
* **Method**: `DELETE`
* **URL**: `http://localhost:8080/api/contents/11`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `204 No Content`

---

## 👥 2. User & Public Creator Profile Module (`/api/creators`)

### 2.1 View Public Creator Profile
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/creators/202`
* **Auth**: Public
* **Expected Response**: `200 OK` with creator user profile payload details.

### 2.2 View Public Creator's Publications List
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/creators/202/contents`
* **Auth**: Public
* **Expected Response**: `200 OK` with the list of contents authored by this creator.

---

## 📅 3. Mentorship & Session Module (`/api/sessions` & `/api/users/{id}/sessions`)

### 3.1 Book/Schedule a Live Doubt Slot
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/sessions/book`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Headers**: `Content-Type: application/json`
* **Request Body**:
  ```json
  {
    "learner_id": 101,
    "creator_id": 202,
    "topic": "Spring Security JWT configuration issues",
    "scheduled_at": "2026-08-15T10:00:00",
    "duration_minutes": 45,
    "session_price": 250.00
  }
  ```
* **Expected Response**: `201 Created` with booking receipt and dynamic `jitsi_meeting_link` field.

### 3.2 View My Scheduled Doubt Sessions List
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/users/101/sessions`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK` (list of sessions where user is either learner or creator).

### 3.3 Confirm Doubt Session Booking Payment
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/mentorship/confirm/1?transaction_id=TXN_LIVE_9988`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK` (bookingStatus changes to `CONFIRMED` and paymentStatus to `PAID`).

---

## 👑 4. Admin Dashboard Module (`/api/admin`)

### 4.1 Get Platform KPI Statistics
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/admin/analytics`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK`
  ```json
  {
    "total_users": 3,
    "total_contents": 2,
    "total_revenue": 14200.0,
    "health_index": 98
  }
  ```

### 4.2 List and Search Users
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/admin/users?search=Arjun`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK` (paginated list of matching users).

### 4.3 Freeze / Suspend User status
* **Method**: `PATCH` (Also supports `POST`)
* **URL**: `http://localhost:8080/api/admin/users/101/status`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK` (Toggles status between `ACTIVE` and `FROZEN`).

### 4.4 Content Moderation (Approve/Flag Uploads)
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/admin/contents/10/flag`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK` (Sets `approval_status` to `FLAGGED`).

### 4.5 Content Moderation (Approve Uploads)
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/admin/contents/10/approve`
* **Auth**: Authenticated (`admin` / `admin123`)
* **Expected Response**: `200 OK` (Sets `approval_status` to `APPROVED`).
