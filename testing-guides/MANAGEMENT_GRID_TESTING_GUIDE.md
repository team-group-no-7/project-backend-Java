# 🎛️ LearnHub Creator Management Grid & Resource Editing Testing Guide

> This guide explains how to test the Creator Management Grid & Editing APIs built in `feature/management-grid`.
> For the complete documentation file, see [src/main/java/com/learnhub/backend/catalog/MANAGEMENT_GRID_TESTING.md](src/main/java/com/learnhub/backend/catalog/MANAGEMENT_GRID_TESTING.md).

---

## ⚡ Quick Testing Steps

### 1. Fetch My Resources Grid (`GET /api/creator/content/my-resources/{creatorId}`)
- **URL**: `http://localhost:8080/api/creator/content/my-resources/1`
- **Header**: `Authorization: Bearer <your-jwt-token>`

### 2. Edit Resource (`PUT /api/creator/content/{id}`)
- **URL**: `http://localhost:8080/api/creator/content/1`
- **Header**: `Authorization: Bearer <your-jwt-token>`
- **Body**:
  ```json
  {
    "title": "Mastering Spring Boot 4 (Updated)",
    "price": 599.00,
    "status": "PUBLISHED"
  }
  ```

### 3. Toggle Status (`PATCH /api/creator/content/{id}/status`)
- **URL**: `http://localhost:8080/api/creator/content/1/status`
- **Body**: `{"status": "DRAFT"}`

### 4. Delete Resource (`DELETE /api/creator/content/{id}`)
- **URL**: `http://localhost:8080/api/creator/content/1`
