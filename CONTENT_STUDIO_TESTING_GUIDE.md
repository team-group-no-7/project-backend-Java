# 📝 LearnHub Content Authoring Studio Testing Guide

> This guide explains how to test the Content Studio APIs built in `feature/content-studio`.
> For the complete documentation file, see [src/main/java/com/learnhub/backend/catalog/CONTENT_STUDIO_TESTING.md](src/main/java/com/learnhub/backend/catalog/CONTENT_STUDIO_TESTING.md).

---

## ⚡ Quick Testing Steps

### 1. Publish Article (`POST /api/creator/content/article`)
- **URL**: `http://localhost:8080/api/creator/content/article`
- **Header**: `Authorization: Bearer <your-jwt-token>`
- **Body**:
  ```json
  {
    "title": "Mastering Spring Boot 4",
    "description": "Comprehensive guide to building RESTful microservices.",
    "previewText": "Learn REST APIs and JPA.",
    "contentBody": "<h1>Spring Boot Guide</h1><p>Rich text article content...</p>",
    "price": 499.00,
    "type": "ARTICLE",
    "level": "Intermediate",
    "tags": "Java, Spring Boot",
    "status": "PUBLISHED",
    "categoryId": 1,
    "creatorId": 1
  }
  ```

### 2. Upload PDF Resource (`POST /api/creator/content/pdf`)
- **URL**: `http://localhost:8080/api/creator/content/pdf`
- **Header**: `Authorization: Bearer <your-jwt-token>`
- **Body (`form-data`)**: `file` (PDF file), `title`, `price`, `categoryId`, `creatorId`.
