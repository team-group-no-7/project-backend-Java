# 🎓 LearnHub Backend (Spring Boot & PostgreSQL)

LearnHub Backend is a robust, learner-focused RESTful web service built with a **Java Spring Boot Modular Monolith** architecture and **PostgreSQL**. It powers user authentication (JWT), role-based authorization, digital study guide distribution, 1-on-1 live mentorship scheduling, Q&A community forums, payment verification, and administrative refund controls.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [System Architecture & Modular Monolith](#-system-architecture--modular-monolith)
- [Package & Module Structure](#-package--module-structure)
- [Technology Stack](#-technology-stack)
- [Database Architecture & Initialization](#-database-architecture--initialization)
- [Security, Authentication & Ownership Validation](#-security-authentication--ownership-validation)
- [Payment Gateway Integration](#-payment-gateway-integration)
- [API Documentation (Swagger / OpenAPI)](#-api-documentation-swagger--openapi)
- [Environment Variables](#-environment-variables)
- [Pre-Configured Demo Accounts](#-pre-configured-demo-accounts)
- [Setup & Execution Guide](#-setup--execution-guide)
- [Troubleshooting & FAQs](#-troubleshooting--faqs)
- [Contribution Guidelines](#-contribution-guidelines)

---

## 🎯 Project Overview

LearnHub Backend provides secure REST API services for technical study material publishing, inline PDF file streaming, interactive Q&A discussions, mentorship scheduling, and payment handling. It features automated PostgreSQL schema initialization, JWT token issuance with refresh token rotation, role-based authorization (`LEARNER`, `CREATOR`, `ADMIN`), and fine-grained resource ownership validation.

---

## 🏛️ System Architecture & Modular Monolith

LearnHub Backend is designed as a **Modular Monolith** organized into vertical domain packages. Each module manages its own REST controllers, business services, Spring Data JPA repositories, and database entities.

```
com.learnhub.backend
 ├── common           ── (Cross-cutting JWT utilities, SecurityConfig, CorsConfig, GlobalExceptionHandler)
 └── modules
      ├── auth        ── (Authentication, JWT Token Issuance, Refresh Tokens)
      ├── user        ── (Learner/Creator Profiles, Role Upgrades, Public Creator Profiles)
      ├── resource    ── (Catalog REST APIs, PDF Uploads, Landing Page Aggregators, Reviews)
      ├── payment     ── (Purchases, Razorpay Payment Gateway, Admin Refund Controller)
      ├── discussion  ── (Content Q&A Threads, Verified Creator Replies, Upvote Counters)
      └── mentorship  ── (1:1 Live Session Booking & Jitsi Video Call Integration)
```

---

## 📦 Package & Module Structure

```
project-backend-Java/src/main/java/com/learnhub/backend/
├── common/
│   ├── config/         # SecurityConfig, CorsConfig, OpenApiConfig
│   ├── dto/            # ApiResponse standard JSON wrapper
│   ├── exception/      # GlobalExceptionHandler, ResourceNotFoundException, BadRequestException
│   └── util/           # JwtTokenProvider, SecurityUtils (Ownership Validation Helper)
└── modules/
    ├── auth/           # AuthController, AuthService, JwtAuthResponse, LoginRequest, RegisterRequest
    ├── user/           # UserController, ProfileController, PublicCreatorProfileController, User Entity
    ├── resource/       # CatalogController, CreatorContentController, LandingPageController, Content Entity
    ├── payment/        # PaymentController, PurchaseController, AdminPaymentController, Purchase Entity
    ├── discussion/     # QAThreadController, QAThread & QAReply Entities
    └── mentorship/     # MentorshipController, DoubtSession Entity
```

---

## 🛠️ Technology Stack

Verified directly from [`pom.xml`](pom.xml):

* **Java Version**: `17`
* **Spring Boot Framework**: `4.1.0`
* **Data Access & ORM**: Spring Data JPA & Hibernate (`PostgreSQLDialect`)
* **Database**: PostgreSQL (v14+)
* **Security & Token Management**: Spring Security + Stateless JWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson` `0.11.5`)
* **API Specs & UI**: `springdoc-openapi-starter-webmvc-ui` `2.8.5`
* **Build & Dependency Management**: Maven Wrapper (`mvnw` / `mvnw.cmd`)

---

## 🗄️ Database Architecture & Initialization

LearnHub Backend uses **automated PostgreSQL database initialization** via Spring Boot properties:

1. **Database Creation**: The developer creates an empty PostgreSQL database named `learnhub_db`.
2. **Schema Creation ([`schema.sql`](src/main/resources/schema.sql))**: Executed automatically on startup (`spring.sql.init.mode=always`), creating all 9 tables (`users`, `categories`, `contents`, `purchases`, `questions`, `discussion_replies`, `doubt_sessions`, `reviews`, `refresh_tokens`).
3. **Data Seeding ([`data.sql`](src/main/resources/data.sql))**: Executed automatically after schema creation (`spring.jpa.defer-datasource-initialization=true`), populating categories, demo users, study materials, student reviews, Q&A threads, and doubt sessions using `ON CONFLICT (id) DO NOTHING`.

---

## 🔒 Security, Authentication & Ownership Validation

* **Stateless Authentication**: Powered by Spring Security filter chain and JWT Bearer tokens.
* **Public Unauthenticated Endpoints**: Strictly limited to `/api/auth/**` and `/api/public/**`.
* **Protected Endpoints**: All non-public APIs (`/api/contents/**`, `/api/creators/**`, `/api/purchases/**`, `/api/payment/**`, `/api/sessions/**`, `/api/qa/**`, `/api/users/**`) require Bearer JWT authentication.
* **Ownership Validation**: Enforced via `SecurityUtils.validateOwnershipById(...)` (for user profile edits) and `SecurityUtils.validateOwnership(creatorEmail)` (for creator content CRUD operations).

---

## 💳 Payment Gateway Integration

* **Implementation**: Integrated with Razorpay Order API (`RazorpayClient`) for real payment creation, accompanied by a payment verification endpoint (`POST /api/payment/verify`) and administrative **Process Refund** functionality (`POST /api/admin/transactions/{id}/refund`).

---

## 📖 API Documentation (Swagger / OpenAPI)

Verified from backend OpenAPI configuration:

* **Swagger UI URL**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **OpenAPI Specs JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🌐 Environment Variables

Supported in [`application.properties`](src/main/resources/application.properties):

| Variable Name | Default Fallback Value | Description |
| :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/learnhub_db` | PostgreSQL JDBC Connection URL |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Database Username |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` | Database Password |
| `JWT_SECRET` | `learnhub-secret-key-for-jwt-signing...` | HS256 JWT Signing Key (min 256 bits) |
| `JWT_EXPIRATION` | `86400000` (24 Hours) | JWT Access Token Expiry in ms |

---

## 🔑 Pre-Configured Demo Accounts

Verified directly from [`data.sql`](src/main/resources/data.sql):

| Role | Email Address | Password | Privileges |
| :--- | :--- | :--- | :--- |
| **Learner** | `arjun.mehta@learnhub.com` | `password123` | Read APIs, Purchase Endpoints, Q&A |
| **Learner** | `priya.sharma@learnhub.com` | `password123` | Read APIs, Reviews, Q&A, Doubt Sessions |
| **Creator** | `rohan.verma@learnhub.com` | `password123` | Content CRUD, PDF Uploads, Q&A Replies |
| **Creator** | `neha.gupta@learnhub.com` | `password123` | System Design Content Publishing |
| **Admin** | `admin@learnhub.com` | `admin123` | Admin User/Resource/Txn Endpoints, Refunds |

---

## 🚀 Setup & Execution Guide

### 1. Create PostgreSQL Database
```sql
CREATE DATABASE learnhub_db;
```

### 2. Run Backend Application
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
*Backend server starts at `http://localhost:8080`.*

---

## ❓ Troubleshooting & FAQs

* **Issue: Database connection fails on startup**
  * *Solution*: Ensure PostgreSQL is running on port 5432 and database `learnhub_db` exists.
* **Issue: `JWT_SECRET` warning**
  * *Solution*: Set environment variable `JWT_SECRET` to a string of at least 32 characters.

---

## 🐳 Docker Containerization Guide

LearnHub Backend includes a beginner-friendly `Dockerfile` and `docker-compose.yml` suitable for CDAC project demonstrations and viva:

### 1. Build Backend Docker Image
```bash
docker build -t learnhub-backend .
```

### 2. Run Backend Container
```bash
docker run -p 8080:8080 --name learnhub-backend-app learnhub-backend
```

### 3. Run Full Stack with Docker Compose
```bash
docker compose up --build
```
*Starts PostgreSQL (`5432`), Spring Boot Backend (`8080`), and React Frontend (`5173`).*

---

## 👥 Contribution Guidelines

* Follow Modular Monolith package organization (`common/`, `modules/<domain>`).
* Ensure all REST controllers pass `@PreAuthorize("isAuthenticated()")` or role-based security checks.
