# 🔐 LearnHub Authentication Module (`com.learnhub.backend.auth`)

> **Developer/Owner:** Yashwant  
> **Branch:** `feature/auth`  
> **Tech Stack:** Spring Boot 4.1.0, Spring Security, JWT (JJWT 0.13.0), BCrypt, PostgreSQL  

Welcome to the **Auth Module Testing Guide**! This document provides a complete, step-by-step walkthrough for testing all authentication endpoints in Postman, Thunder Client, or cURL.

---

## 📌 Architecture Summary

- **Authentication Strategy:** Stateless JWT (JSON Web Token) + Refresh Token pattern.
- **Password Security:** Passwords hashed using **BCryptPasswordEncoder**.
- **Access Tokens:** Short-lived JWTs valid for **24 hours** (sent via `Authorization: Bearer <token>`).
- **Refresh Tokens:** Long-lived UUID tokens valid for **7 days** stored in the `refresh_tokens` database table.
- **Default User Role:** Every new user registered via `/api/auth/register` is automatically assigned `role: "LEARNER"`.

---

## ⚙️ Prerequisites Before Testing

1. **PostgreSQL Database Running**:
   Ensure PostgreSQL is running locally on port `5432` with database `learnhub_db`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/learnhub_db
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

2. **Start the Backend Application**:
   Open a terminal in `project-backend-Java` directory and run:
   ```powershell
   .\mvnw spring-boot:run
   ```
   *The server will start at `http://localhost:8080`.*

---

## 🧪 Step-by-Step API Testing Workflow

Follow these steps in exact order to test the complete authentication flow.

---

### Step 1: Check Auth Module Health Status
Verify that the Auth module and backend are up and running.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/auth/status`
- **Headers:** None required
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Authentication Module is Active",
    "data": "OK"
  }
  ```

---

### Step 2: Register a New User
Create a new user account on LearnHub.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/auth/register`
- **Headers:** `Content-Type: application/json`
- **Request Body (JSON):**
  ```json
  {
    "name": "Rohan Sharma",
    "email": "rohan@learnhub.com",
    "password": "password123"
  }
  ```
- **Expected Status:** `201 Created`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "User registered successfully",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2hhbkBsZWFybmh1Yi5jb20iLCJyb2xlIjoiTEVBUk5FUiIsImlhdCI6MTc0M...",
      "refreshToken": "e3a89042-b91c-4f1e-87a4-23910c283abc",
      "id": 1,
      "name": "Rohan Sharma",
      "email": "rohan@learnhub.com",
      "role": "LEARNER"
    }
  }
  ```
> 💡 **Important:** Copy the `token` and `refreshToken` values from the response. You will need them for subsequent steps!

---

### Step 3: Test Duplicate Registration Validation
Verify that the system blocks registering with an email that already exists.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/auth/register`
- **Headers:** `Content-Type: application/json`
- **Request Body (JSON):** *(Same email as Step 2)*
  ```json
  {
    "name": "Rohan Duplicate",
    "email": "rohan@learnhub.com",
    "password": "password123"
  }
  ```
- **Expected Status:** `400 Bad Request`
- **Expected Response:**
  ```json
  {
    "success": false,
    "message": "Email already registered",
    "data": null
  }
  ```

---

### Step 4: Login with Existing User Credentials
Authenticate with your registered credentials.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/auth/login`
- **Headers:** `Content-Type: application/json`
- **Request Body (JSON):**
  ```json
  {
    "email": "rohan@learnhub.com",
    "password": "password123"
  }
  ```
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Login successful",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "refreshToken": "e3a89042-b91c-4f1e-87a4-23910c283abc",
      "id": 1,
      "name": "Rohan Sharma",
      "email": "rohan@learnhub.com",
      "role": "LEARNER"
    }
  }
  ```

---

### Step 5: Test Protected Endpoint WITHOUT JWT Token
Verify that Spring Security blocks unauthorized requests to protected routes.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/users/all`
- **Headers:** None
- **Expected Status:** `401 Unauthorized` or `403 Forbidden`

---

### Step 6: Test Protected Endpoint WITH JWT Bearer Token
Verify that sending a valid JWT token allows access to protected endpoints.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/users/all`
- **Headers:**
  - `Authorization`: `Bearer <paste-your-jwt-token-here>`
- **Postman Setup:**
  - Go to the **Authorization** tab in Postman
  - Type: **Bearer Token**
  - Token: Paste the `token` string received from Login/Register
- **Expected Status:** `200 OK`
- **Expected Response:** Returns list of all users.

---

### Step 7: Refresh Access Token
Generate a brand new JWT token using your refresh token without re-entering password.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/auth/refresh`
- **Headers:** `Content-Type: application/json`
- **Request Body (JSON):**
  ```json
  {
    "refreshToken": "e3a89042-b91c-4f1e-87a4-23910c283abc"
  }
  ```
- **Expected Status:** `200 OK`
- **Expected Response:** Returns new `token` (JWT).

---

### Step 8: User Logout
Revoke the user's refresh token on logout.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/auth/logout`
- **Headers:** `Content-Type: application/json`
- **Request Body (JSON):**
  ```json
  {
    "refreshToken": "e3a89042-b91c-4f1e-87a4-23910c283abc"
  }
  ```
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Logged out successfully",
    "data": null
  }
  ```

---

### Step 9: Verify Revoked Refresh Token
Confirm that attempting to refresh using a logged-out token fails.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/auth/refresh`
- **Headers:** `Content-Type: application/json`
- **Request Body (JSON):** *(Same revoked refresh token from Step 8)*
  ```json
  {
    "refreshToken": "e3a89042-b91c-4f1e-87a4-23910c283abc"
  }
  ```
- **Expected Status:** `400 Bad Request`
- **Expected Response:**
  ```json
  {
    "success": false,
    "message": "Refresh token has been revoked",
    "data": null
  }
  ```

---

## 📋 Endpoint Summary Table

| Method | Endpoint | Access | Request Body | Description |
|:---|:---|:---|:---|:---|
| `GET` | `/api/auth/status` | Public | None | Health check for auth module |
| `POST` | `/api/auth/register` | Public | `RegisterRequest` | Registers user & issues tokens |
| `POST` | `/api/auth/login` | Public | `LoginRequest` | Validates credentials & issues tokens |
| `POST` | `/api/auth/refresh` | Public | `RefreshTokenRequest` | Issues new JWT using refresh token |
| `POST` | `/api/auth/logout` | Public | `RefreshTokenRequest` | Revokes refresh token |

---

## ⚠️ Common Error Codes & Troubleshooting

| Status Code | Meaning | Cause | Solution |
|:---|:---|:---|:---|
| **`400 Bad Request`** | Validation / Logic Error | Invalid email, short password, email already exists, or revoked refresh token. | Check the response `message` for exact details. |
| **`401 Unauthorized`** | Authentication Missing | Missing or invalid `Authorization: Bearer <token>` header on protected route. | Ensure token is passed in Authorization header. |
| **`500 Internal Server Error`** | Server / Database Exception | Database connection failure or unhandled exception. | Check backend terminal console logs. |

---

## 🤝 Questions or Support?
If you encounter any issues while testing auth endpoints, contact **Yashwant** on the team channel!
