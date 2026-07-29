# 📝 LearnHub Content Authoring Studio (`com.learnhub.backend.catalog`)

> **Developer/Owner:** Yashwant  
> **Branch:** `feature/content-studio`  
> **Tech Stack:** Spring Boot 4.1.0, Spring Data JPA, PostgreSQL, JWT Authentication, Multipart File Upload  

Welcome to the **Content Authoring Studio Testing Guide**! This document provides a complete walkthrough for testing resource publishing endpoints (Rich Text Articles & PDF Uploads).

---

## 📌 Architecture Summary

- **Module Purpose:** Enables Content Creators to publish Rich Text Articles (WYSIWYG editor input) and Multipart PDF document resources.
- **Authentication:** All content creation endpoints require a valid JWT Bearer Token passed in the HTTP request header:
  `Authorization: Bearer <your-jwt-token>`
- **Category Auto-Increment:** Whenever a creator publishes a resource under a `categoryId`, the database `resource_count` for that category is automatically incremented.
- **Status Support:** Resources support `DRAFT` and `PUBLISHED` status.

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

3. **Database Seed Data Requirement**:
   Ensure Category ID `1` exists in the `categories` table (seeded via `schema.sql` / `data.sql`).

---

## 🧪 Step-by-Step API Testing Workflow

---

### Step 1: Check Content Studio Health Status
Verify that the Content Studio module is active.

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/creator/content/status`
- **Headers:** None required
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Content Authoring Studio Module is Active",
    "data": "OK"
  }
  ```

---

### Step 2: Publish Rich Text WYSIWYG Article
Publish an article written using the frontend WYSIWYG rich text editor.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/creator/content/article`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
  - `Content-Type`: `application/json`
- **Request Body (JSON):**
  ```json
  {
    "title": "Mastering Spring Boot 4 & Microservices",
    "description": "Comprehensive guide to building production-grade Spring Boot microservices.",
    "previewText": "Learn REST APIs, Spring Security, and JPA.",
    "contentBody": "<h1>Spring Boot 4 Guide</h1><p>Welcome to this <strong>comprehensive tutorial</strong> on building RESTful microservices.</p>",
    "price": 499.00,
    "type": "ARTICLE",
    "level": "Intermediate",
    "tags": "Java, Spring Boot, Microservices",
    "status": "PUBLISHED",
    "categoryId": 1,
    "creatorId": 1
  }
  ```
- **Expected Status:** `201 Created`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Article published successfully",
    "data": {
      "id": 1,
      "title": "Mastering Spring Boot 4 & Microservices",
      "description": "Comprehensive guide to building production-grade Spring Boot microservices.",
      "previewText": "Learn REST APIs, Spring Security, and JPA.",
      "contentBody": "<h1>Spring Boot 4 Guide</h1><p>Welcome to this <strong>comprehensive tutorial</strong> on building RESTful microservices.</p>",
      "fileUrl": null,
      "price": 499.0,
      "type": "ARTICLE",
      "level": "Intermediate",
      "tags": "Java, Spring Boot, Microservices",
      "status": "PUBLISHED",
      "categoryId": 1,
      "creatorId": 1,
      "createdAt": "2026-07-30T01:52:00"
    }
  }
  ```

---

### Step 3: Upload PDF Resource (`multipart/form-data`)
Upload a PDF document directly using multipart form data in Postman.

- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/api/creator/content/pdf`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
  - *(Do NOT set Content-Type header manually; Postman will automatically set boundary for `form-data`)*
- **Body (`form-data`):**
  - `file` (File type): Select a PDF file from your computer
  - `title` (Text): `Complete Java 21 Reference PDF`
  - `description` (Text): `Official cheatsheet for Java 21 features.`
  - `price` (Text): `299.00`
  - `level` (Text): `Advanced`
  - `tags` (Text): `Java, PDF, Cheatsheet`
  - `status` (Text): `PUBLISHED`
  - `categoryId` (Text): `1`
  - `creatorId` (Text): `1`

- **Expected Status:** `201 Created`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "PDF resource uploaded and published successfully",
    "data": {
      "id": 2,
      "title": "Complete Java 21 Reference PDF",
      "fileUrl": "/uploads/pdfs/a1b2c3d4-sample.pdf",
      "price": 299.0,
      "type": "PDF",
      "status": "PUBLISHED",
      "categoryId": 1,
      "creatorId": 1
    }
  }
  ```

---

## 📋 Endpoint Summary Table

| Method | Endpoint | Access | Content-Type | Description |
|:---|:---|:---|:---|:---|
| `GET` | `/api/creator/content/status` | Public | None | Health check for Content Studio |
| `POST` | `/api/creator/content/article` | JWT Protected | `application/json` | Publish Rich Text WYSIWYG Article |
| `POST` | `/api/creator/content/pdf` | JWT Protected | `multipart/form-data` | Upload PDF Document File |
| `POST` | `/api/creator/content` | JWT Protected | `application/json` | Generic Resource Creation Endpoint |

---

## 🤝 Support
If you encounter any issues while testing Content Studio endpoints, contact **Yashwant** on the team channel!
