# 👤 LearnHub Creator Profile Module Testing Guide

> This guide explains how to test the Creator & User Profile APIs built in `feature/creator-profile`.
> For the complete documentation file, see [src/main/java/com/learnhub/backend/user/USER_PROFILE_TESTING.md](src/main/java/com/learnhub/backend/user/USER_PROFILE_TESTING.md).

---

## ⚡ Quick Testing Steps

### 1. View User Profile (`GET /api/users/{id}`)
- **URL**: `http://localhost:8080/api/users/1`
- **Header**: `Authorization: Bearer <your-jwt-token>`

### 2. Update Creator Profile (`PUT /api/users/{id}`)
- **URL**: `http://localhost:8080/api/users/1`
- **Header**: `Authorization: Bearer <your-jwt-token>`
- **Body**:
  ```json
  {
    "name": "Rohan Sharma",
    "headline": "Senior Full-Stack Creator & Java Architect",
    "location": "Bengaluru, India",
    "avatarUrl": "https://images.unsplash.com/photo-1534528741775-53994a69daeb"
  }
  ```

### 3. Become Creator (`PATCH /api/users/{id}/become-creator`)
- **URL**: `http://localhost:8080/api/users/1/become-creator`
- **Header**: `Authorization: Bearer <your-jwt-token>`
- **Response**: Upgrades user role in PostgreSQL database to `CREATOR`.

### 4. Check All Users (`GET /api/users/all`)
- **URL**: `http://localhost:8080/api/users/all`
- **Header**: `Authorization: Bearer <your-jwt-token>`
