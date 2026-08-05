# 🎓 LearnHub — Developer Knowledge & Technical Marketplace Platform

LearnHub is a full-stack, learner-focused technical knowledge marketplace built with a **Java Spring Boot Modular Monolith** backend and a **React 18 + Vite + TailwindCSS** frontend. The platform enables verified developers to publish structured notes, guides, and architecture blueprints, while allowing learners to purchase study materials, read inline via a built-in PDF viewer, engage in Q&A discussion forums, and schedule 1:1 live video doubt sessions.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Problem Statement & Solution](#-problem-statement--solution)
- [System Architecture](#-system-architecture)
- [Modular Monolith Package Structure](#-modular-monolith-package-structure)
- [Frontend Structure](#-frontend-structure)
- [Technology Stack](#-technology-stack)
- [Database Architecture & Initialization](#-database-architecture--initialization)
- [Security, Auth & Ownership Validation](#-security-auth--ownership-validation)
- [Payment Gateway Integration](#-payment-gateway-integration)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [Environment Variables](#-environment-variables)
- [Pre-Configured Demo Accounts](#-pre-configured-demo-accounts)
- [Setup & Execution Guide](#-setup--execution-guide)
- [Future Enhancements](#-future-enhancements)
- [Contributors](#-contributors)

---

## 🎯 Project Overview

LearnHub is designed to solve the fragmentation of technical study resources by providing an end-to-end marketplace for developers, authors, and engineering students. Verified technical creators upload PDF study guides, set custom pricing, and provide 1:1 mentorship, while learners enjoy instant entitlement upon purchase, inline reading, and interactive Q&A discussion threads.

---

## 💡 Problem Statement & Solution

* **Problem**: Fragmented study materials, poor quality unverified PDF guides, lack of direct interaction with technical authors, and rigid subscription models.
* **Solution**: A unified marketplace with verified creator profiles, instant inline document streaming, direct 1:1 video doubt sessions via Jitsi Meet, and a pay-per-resource entitlement model.

---

## 🏛️ System Architecture

LearnHub follows a **Modular Monolith** pattern organized into clean vertical domain packages. Each module owns its REST controllers, services, repositories, and domain entities, preparing the application for seamless microservices decomposition if required.

```
LearnHub Monolith Architecture
 ├── Frontend Client (React 18 + Vite + Axios + Tailwind CSS)
 └── Backend REST API (Spring Boot 4.1.0 + Spring Security + JWT + PostgreSQL)
      ├── auth        ── (Authentication, Token Issuance, Refresh Tokens)
      ├── user        ── (Learner/Creator Profiles, Role Upgrades, Public Profiles)
      ├── resource    ── (Marketplace Catalog, PDF Uploads, Landing Aggregations, Reviews)
      ├── payment     ── (Purchases, Razorpay Integration, Admin Refunds)
      ├── discussion  ── (Content Q&A Threads, Verified Creator Replies, Upvotes)
      └── mentorship  ── (1:1 Live Session Scheduling & Jitsi Video Call Integration)
```

---

## 📦 Modular Monolith Package Structure

```
project-backend-Java/src/main/java/com/learnhub/backend/
├── common/
│   ├── config/         # SecurityConfig, CorsConfig, OpenApiConfig
│   ├── dto/            # ApiResponse standard wrapper
│   ├── exception/      # GlobalExceptionHandler, ResourceNotFoundException, BadRequestException
│   └── util/           # JwtTokenProvider, SecurityUtils (Ownership Validation)
└── modules/
    ├── auth/           # AuthController, AuthService, JwtAuthResponse, LoginRequest, RegisterRequest
    ├── user/           # UserController, ProfileController, PublicCreatorProfileController, User Entity
    ├── resource/       # CatalogController, CreatorContentController, LandingPageController, Content Entity
    ├── payment/        # PaymentController, PurchaseController, AdminPaymentController, Purchase Entity
    ├── discussion/     # QAThreadController, QAThread & QAReply Entities
    └── mentorship/     # MentorshipController, DoubtSession Entity
```

---

## 🎨 Frontend Structure

```
project-frontend-react/
├── public/             # Static public assets
├── src/
│   ├── assets/         # Images and SVG icons
│   ├── components/     # MarketplaceCard, ProfileSidebar, LearnerDashboard, CreatorDashboard
│   ├── pages/          # LandingPage, MarketplacePage, ResourceDetailPage, CreatorProfilePage, AdminDashboardPage
│   ├── routes/         # AppRoutes (Global Route Protection Guards)
│   ├── utils/          # api.js (Axios instance with Bearer JWT Interceptor)
│   ├── App.jsx         # Main application orchestrator & state manager
│   └── main.jsx        # React DOM entry point
├── index.html
├── package.json
└── vite.config.js
```

---

## 🛠️ Technology Stack

Verified directly from project build configuration:

### Backend Stack ([`pom.xml`](pom.xml))
* **Java Version**: `17`
* **Spring Boot**: `4.1.0`
* **Data Access**: Spring Data JPA & Hibernate (`PostgreSQLDialect`)
* **Database**: PostgreSQL (v14+)
* **Security**: Spring Security + JWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson` 0.11.5)
* **API Specs**: `springdoc-openapi-starter-webmvc-ui` `2.8.5`
* **Build System**: Maven (`mvnw` / `mvnw.cmd`)

### Frontend Stack ([`package.json`](../project-frontend-react/package.json))
* **Framework**: React `^18.3.1`
* **Build Tool**: Vite `^6.1.0`
* **Routing**: React Router DOM `^7.1.5`
* **HTTP Client**: Axios `^1.7.9`
* **Styling**: Tailwind CSS `^3.4.17` + Lucide React Icons `^0.475.0`

---

## 🗄️ Database Architecture & Initialization

LearnHub uses **automated PostgreSQL database initialization** without requiring manual SQL script execution:

1. **Database Creation**: The developer creates an empty PostgreSQL database named `learnhub_db`.
2. **Schema Creation ([`schema.sql`](src/main/resources/schema.sql))**: Spring Boot (`spring.sql.init.mode=always`) executes `schema.sql` on startup, creating all 9 tables (`users`, `categories`, `contents`, `purchases`, `questions`, `discussion_replies`, `doubt_sessions`, `reviews`, `refresh_tokens`).
3. **Data Seeding ([`data.sql`](src/main/resources/data.sql))**: Hibernate (`spring.jpa.defer-datasource-initialization=true`) runs `data.sql` after table creation, populating seed users, categories, study materials, student reviews, Q&A threads, and mentorship sessions using `ON CONFLICT (id) DO NOTHING`.

---

## 🔒 Security, Auth & Ownership Validation

* **Stateless Authentication**: Powered by Spring Security and JWT Bearer tokens.
* **Public Unauthenticated Routes**: Strictly limited to `/api/auth/**` and `/api/public/**`.
* **Protected Routes**: All workspace APIs (`/api/contents/**`, `/api/creators/**`, `/api/purchases/**`, `/api/payment/**`, `/api/sessions/**`, `/api/qa/**`, `/api/users/**`) require valid JWT authentication.
* **Ownership Validation**: Enforced via `SecurityUtils.validateOwnershipById(...)` (for profile edits) and `SecurityUtils.validateOwnership(creatorEmail)` (for creator content CRUD operations).

---

## 💳 Payment Gateway Integration

* **Implementation**: Integrated with Razorpay Order API (`RazorpayClient`) for real payment creation, accompanied by a verified payment verification endpoint (`POST /api/payment/verify`) and administrative **Process Refund** functionality (`POST /api/admin/transactions/{id}/refund`).

---

## 📖 API Documentation (Swagger)

Verified from application OpenAPI configuration:

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
| `VITE_API_BASE_URL` | `http://localhost:8080` | Frontend API Base URL |

---

## 🔑 Pre-Configured Demo Accounts

Verified directly from [`data.sql`](src/main/resources/data.sql):

| Role | Email Address | Password | Functionality |
| :--- | :--- | :--- | :--- |
| **Learner** | `arjun.mehta@learnhub.com` | `password123` | Browse Marketplace, Purchase Guides, Read Inline, Ask Q&A |
| **Learner** | `priya.sharma@learnhub.com` | `password123` | Browse Marketplace, Write Reviews, Ask Q&A, Book Mentorship |
| **Creator** | `rohan.verma@learnhub.com` | `password123` | Content Studio Uploads, PDF Management Grid, Reply to Q&A |
| **Creator** | `neha.gupta@learnhub.com` | `password123` | System Design Blueprint Publishing, Earn Revenue |
| **Admin** | `admin@learnhub.com` | `admin123` | Admin Panel Oversight, User/Resource/Txn Grids, Process Refunds |

---

## 🚀 Setup & Execution Guide

### 1. Create PostgreSQL Database
```sql
CREATE DATABASE learnhub_db;
```

### 2. Launch Backend
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

### 3. Launch Frontend
```bash
cd project-frontend-react
npm install
npm run dev
```

---

## 🔮 Future Enhancements

* Microservices decomposition for billing and streaming modules.
* Interactive code sandbox playground integration inside the PDF viewer.
* Live WebSocket chat for mentorship doubt sessions.

---

## 👥 Contributors

* **LearnHub Development Team** (Team Group No. 7)
