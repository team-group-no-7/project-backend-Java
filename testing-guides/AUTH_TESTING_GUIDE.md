# 🔐 LearnHub Auth Module Testing Guide

> This guide explains how to test the Auth APIs built in `feature/auth`.
> For the complete documentation file, see [src/main/java/com/learnhub/backend/auth/README.md](src/main/java/com/learnhub/backend/auth/README.md).

---

## ⚡ Quick Testing Steps

### 1. Register User (`POST /api/auth/register`)
- **URL**: `http://localhost:8080/api/auth/register`
- **Body**:
  ```json
  {
    "name": "Rohan Sharma",
    "email": "rohan@learnhub.com",
    "password": "password123"
  }
  ```

### 2. Login User (`POST /api/auth/login`)
- **URL**: `http://localhost:8080/api/auth/login`
- **Body**:
  ```json
  {
    "email": "rohan@learnhub.com",
    "password": "password123"
  }
  ```
- **Returns**: `token` (JWT) and `refreshToken`.

### 3. Access Protected Endpoint (`GET /api/users/all`)
- **Header**: `Authorization: Bearer <your-jwt-token>`

### 4. Refresh Token (`POST /api/auth/refresh`)
- **Body**:
  ```json
  {
    "refreshToken": "<your-refresh-token-uuid>"
  }
  ```

### 5. Logout (`POST /api/auth/logout`)
- **Body**:
  ```json
  {
    "refreshToken": "<your-refresh-token-uuid>"
  }
  ```
