# 🎛️ LearnHub Creator Management Grid & Resource Editing (`com.learnhub.backend.catalog`)

> **Developer/Owner:** Yashwant  
> **Branch:** `feature/management-grid`  
> **Tech Stack:** Spring Boot 4.1.0, Spring Data JPA, PostgreSQL, JWT Authentication  

Welcome to the **Creator Management Grid & Resource Editing Testing Guide**! This document provides a complete walkthrough for testing the creator's resource management grid, resource editing, status toggling (`DRAFT` vs `PUBLISHED`), and resource deletion endpoints.

---

## 📌 Architecture Summary

- **Module Purpose:** Provides full CRUD management for creator learning resources (Articles & PDF uploads).
- **Authentication:** All management grid endpoints require a valid JWT Bearer Token passed in the HTTP request header:
  `Authorization: Bearer <your-jwt-token>`
- **Category Auto-Adjustment:** Deleting a resource automatically decrements the `resource_count` for that resource's category in the database.

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

### Step 1: Fetch My Resources Grid for Creator
Retrieve all resources uploaded/created by a specific creator (e.g. Creator ID `1`).

- **HTTP Method:** `GET`
- **URL:** `http://localhost:8080/api/creator/content/my-resources/1`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
- **Expected Response:** Returns an array of `ContentResponse` objects created by Creator `1`.

---

### Step 2: Edit / Update Existing Resource (Article or PDF)
Update title, price, description, and rich text content for content ID `1`.

- **HTTP Method:** `PUT`
- **URL:** `http://localhost:8080/api/creator/content/1`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
  - `Content-Type`: `application/json`
- **Request Body (JSON):**
  ```json
  {
    "title": "Mastering Spring Boot 4 & Microservices (Updated Edition)",
    "description": "Updated 2026 edition with advanced Spring Security and reactive patterns.",
    "previewText": "Learn REST APIs, Spring Security, and Reactive Microservices.",
    "contentBody": "<h1>Updated Spring Boot 4 Guide</h1><p>New content added for <strong>Spring Boot 4.1.0</strong>.</p>",
    "price": 599.00,
    "level": "Advanced",
    "tags": "Java, Spring Boot, Microservices, Security",
    "status": "PUBLISHED"
  }
  ```
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Content resource updated successfully",
    "data": {
      "id": 1,
      "title": "Mastering Spring Boot 4 & Microservices (Updated Edition)",
      "price": 599.0,
      "status": "PUBLISHED"
    }
  }
  ```

---

### Step 3: Toggle Resource Status (`DRAFT` vs `PUBLISHED`)
Change a published resource to `DRAFT` (or vice-versa).

- **HTTP Method:** `PATCH`
- **URL:** `http://localhost:8080/api/creator/content/1/status`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
  - `Content-Type`: `application/json`
- **Request Body (JSON):**
  ```json
  {
    "status": "DRAFT"
  }
  ```
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Resource status updated to DRAFT",
    "data": {
      "id": 1,
      "status": "DRAFT"
    }
  }
  ```

---

### Step 4: Delete Learning Resource
Delete a resource by ID.

- **HTTP Method:** `DELETE`
- **URL:** `http://localhost:8080/api/creator/content/1`
- **Headers:**
  - `Authorization`: `Bearer <your-jwt-token>`
- **Expected Status:** `200 OK`
- **Expected Response:**
  ```json
  {
    "success": true,
    "message": "Content resource deleted successfully",
    "data": "Resource 1 deleted"
  }
  ```

---

## 📋 Endpoint Summary Table

| Method | Endpoint | Access | Request Body | Description |
|:---|:---|:---|:---|:---|
| `GET` | `/api/creator/content/my-resources/{creatorId}` | JWT Protected | None | Fetch creator's resource grid |
| `PUT` | `/api/creator/content/{id}` | JWT Protected | `UpdateContentRequest` | Edit / update resource details |
| `PATCH` | `/api/creator/content/{id}/status` | JWT Protected | `ContentStatusRequest` | Toggle resource status (DRAFT/PUBLISHED) |
| `DELETE` | `/api/creator/content/{id}` | JWT Protected | None | Delete resource and update category stats |

---

## 🤝 Support
If you encounter any issues while testing Management Grid endpoints, contact **Yashwant** on the team channel!
