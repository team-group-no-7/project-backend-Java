# 👤 LearnHub User Profile Module (`com.learnhub.backend.user`)

> **Developer/Owner:** Yashwant  
> **Branch:** `feature/creator-profile`  
> **Tech Stack:** Spring Boot 4.1.0, Spring Data JPA, PostgreSQL, JWT Authentication  

Welcome to the **Creator & User Profile Module Testing Guide**! This document provides a complete, step-by-step walkthrough for testing all profile endpoints using Postman, Thunder Client, or cURL.

---

## 📌 Architecture Summary

- **Module Purpose:** Manages user profile information (Name, Email, Role, Avatar URL, Headline, Location, and Join Date).
- **Authentication:** All profile endpoints require a valid JWT Bearer Token passed in the HTTP request header:
  `Authorization: Bearer <your-jwt-token>`
- **Validation:** Profile updates enforce `@NotBlank` validation on required fields.

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
   - `POST http://localhost:8080/api/auth/register`
   - Copy the `"token"` string from the JSON response.

---

## 🧪 Step-by-Step API Testing Workflow

---

### Step 1: Check User Module Health Status
Verify that the User Profile module is active.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/users/status`
- **Headers:** None required
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "User Profile Module is Active",
    "data": "OK"
  }
  ```

---

### Step 2: Fetch All Users
Retrieve a list of all registered users in the database.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/users/all`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
- **Expected Response:** Returns an array of user objects.

---

### Step 3: View User Profile by ID
Fetch public profile details for a specific user ID (e.g. User ID `1`).

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/users/1`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "User profile retrieved successfully",
    "data": {
      "id": 1,
      "name": "Rohan Sharma",
      "email": "rohan@learnhub.com",
      "role": "LEARNER",
      "avatarUrl": null,
      "headline": null,
      "location": null,
      "joinedAt": "2026-07-29T19:54:00"
    }
  }
  ```

---

### Step 4: Update Creator Profile Details
Update profile fields (`name`, `headline`, `location`, `avatarUrl`) for user ID `1`.

- **HTTP Method:** `PUT`
- **URL:** `http://localhost:8080/api/users/1`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
  - `Content-Type`: `application/json`
- **Request Body (JSON):**
  ```json
  {
    "name": "Rohan Sharma",
    "headline": "Senior Full-Stack Creator & Java Architect",
    "location": "Bengaluru, India",
    "avatarUrl": "https://images.unsplash.com/photo-1534528741775-53994a69daeb"
  }
  ```
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "User profile updated successfully",
    "data": {
      "id": 1,
      "name": "Rohan Sharma",
      "email": "rohan@learnhub.com",
      "role": "LEARNER",
      "avatarUrl": "https://images.unsplash.com/photo-1534528741775-53994a69daeb",
      "headline": "Senior Full-Stack Creator & Java Architect",
      "location": "Bengaluru, India",
      "joinedAt": "2026-07-29T19:54:00"
    }
  }
  ```

---

### Step 5: Become Creator (Upgrade Role from LEARNER to CREATOR)
Upgrade user's role from `LEARNER` to `CREATOR` in PostgreSQL database.

- **HTTP Method:** `PATCH`
- **URL:** `http://localhost:8080/api/users/1/become-creator`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Congratulations! You are now a Creator on LearnHub",
    "data": {
      "id": 1,
      "name": "Rohan Sharma",
      "email": "rohan@learnhub.com",
      "role": "CREATOR",
      "avatarUrl": "https://images.unsplash.com/photo-1534528741775-53994a69daeb",
      "headline": "Senior Full-Stack Creator & Java Architect",
      "location": "Bengaluru, India",
      "joinedAt": "2026-07-29T19:54:00"
    }
  }
  ```

---

### Step 6: Test Non-Existent User Lookup (404 Error Check)
Verify that requesting a user ID that does not exist returns a clean `404 Not Found` response.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/users/99999`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `404 Not Found`
- **Expected Response:**
  ```json
  {
    "success": false,
    "message": "User not found with id: 99999",
    "data": null
  }
  ```

---

### Step 6: Test Validation Error Check (400 Error Check)
Verify that attempting to update profile with an empty name triggers `@NotBlank` validation.

- **HTTP Method:** `PUT`
- **URL:** `http://localhost:8080/api/users/1`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
  - `Content-Type`: `application/json`
- **Request Body (JSON):**
  ```json
  {
    "name": "",
    "headline": "Testing Validation"
  }
  ```
- **Expected Status:** `400 Bad Request`
- **Expected Response:**
  ```json
  {
    "success": false,
    "message": "Name cannot be empty",
    "data": {
      "name": "Name cannot be empty"
    }
  }
  ```

---

## 📋 Endpoint Summary Table

| Method | Endpoint | Access | Request Body | Description |
|:---|:---|:---|:---|:---|
| `GET` | `/api/users/status` | Public | None | Health check for User Profile module |
| `GET` | `/api/users/all` | JWT Protected | None | Fetch list of all registered users |
| `GET` | `/api/users/{id}` | JWT Protected | None | Fetch user profile by ID |
| `PUT` | `/api/users/{id}` | JWT Protected | `UpdateProfileRequest` | Update user profile details |
| `PATCH` | `/api/users/{id}/become-creator` | JWT Protected | None | Upgrade user role from LEARNER to CREATOR |

---

## ⚠️ Common Error Codes & Troubleshooting

| Status Code | Meaning | Cause & Solution |
|:---|:---|:---|
| **`400 Bad Request`** | Validation Error | Sent empty name or malformed JSON. Fix request body fields. |
| **`401 Unauthorized`** | Token Missing | Forgot to include `Authorization: Bearer <token>` in Postman headers. |
| **`404 Not Found`** | Resource Missing | User ID passed in URL path does not exist in database. |

---

## 🤝 Support
If you encounter any issues while testing profile endpoints, contact **Yashwant** on the team channel!
