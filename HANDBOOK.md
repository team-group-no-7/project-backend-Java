# 🎓 LearnHub — Beginners Handbook & Complete System Workflows

Welcome to the **LearnHub System Handbook**. This guide provides an end-to-end blueprint of the LearnHub platform, detailing full user workflows, architectural data flows, API mappings, and role-based permissions across both the **React Frontend** and **Spring Boot Java Backend**.

---

## 📌 System Architecture Overview

```mermaid
graph TD
    Client["React 18 Frontend (Vite + TailwindCSS)"]
    API["Axios API Client (@/utils/api.js)"]
    Security["Spring Security (JWT & PermitAll Filters)"]
    Controllers["Spring Boot REST Controllers"]
    Services["Service Layer (JPA Services)"]
    DB[("PostgreSQL Database (learnhub_db)")]

    Client -->|HTTP / REST| API
    API -->|Bearer Token / JSON| Security
    Security --> Controllers
    Controllers --> Services
    Services --> DB
```

---

## 👥 Role-Based System Workflows

LearnHub supports three distinct user roles: **Learner**, **Creator**, and **Admin**.

```mermaid
graph LR
    User([User Auth]) -->|Role: LEARNER| LearnerFlow[Learner Journey]
    User -->|Role: CREATOR| CreatorFlow[Creator Journey]
    User -->|Role: ADMIN| AdminFlow[Admin Journey]

    LearnerFlow --> L1[Explore Marketplace]
    LearnerFlow --> L2[Purchase Content / PDF]
    LearnerFlow --> L3[Unified Content Reader]
    LearnerFlow --> L4[In-Reader Doubts & Q&A]
    LearnerFlow --> L5[Book 1-on-1 Session & Jitsi Call]
    LearnerFlow --> L6[Leave Star Rating & Review]

    CreatorFlow --> C1[Content Studio - Upload PDF/Article]
    CreatorFlow --> C2[Management Grid - Edit/Publish/Unpublish]
    CreatorFlow --> C3[Answer Learner Q&A Doubts]
    CreatorFlow --> C4[View Booked Doubt Sessions & Revenue]

    AdminFlow --> A1[Manage Platform Users]
    AdminFlow --> A2[Freeze / Unfreeze Suspicious Accounts]
    AdminFlow --> A3[Platform Overview & Analytics]
```

---

## 🔄 End-to-End Core Workflows

### Workflow 1: Authentication & Role Switch

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant FE as React Frontend
    participant Auth as AuthController
    participant DB as PostgreSQL DB

    User->>FE: Enter Email & Password
    FE->>Auth: POST /api/auth/login
    Auth->>DB: Verify User Credentials & Freeze Status
    alt Account Frozen
        Auth-->>FE: HTTP 403 (Account is frozen by Admin)
        FE-->>User: Show Error Banner
    else Success
        Auth-->>FE: Return JWT Token & User Profile (Role: LEARNER/CREATOR/ADMIN)
        FE->>FE: Store Token in localStorage
        FE-->>User: Redirect to Role Dashboard
    end
```

---

### Workflow 2: Content Purchasing & Dynamic Revenue Calculation

1. **Browse**: Learner selects a paid resource on the Marketplace (`GET /api/public/contents`).
2. **Checkout**: Learner clicks **Buy Now** -> Navigates to `CheckoutPage.jsx`.
3. **Purchase API**: Frontend calls `POST /api/purchases/buy` with `userId` & `contentId`.
4. **Database Mutation**:
   - Inserts record into PostgreSQL `purchases` table.
   - Increments `learners_count` on `contents` table.
   - Calculates total revenue on Creator side (`SUM(price * learners_count)`).
5. **Access Granted**: Resource appears immediately in **My Library** (`GET /api/user/library`).

---

### Workflow 3: Mentorship Session Booking & Jitsi Video Call

```mermaid
sequenceDiagram
    autonumber
    actor Learner
    actor Creator
    participant FE as React Frontend
    participant Session as MentorshipController
    participant DB as PostgreSQL DB

    Learner->>FE: View Creator Profile -> Book Doubt Session
    FE->>Session: POST /api/mentorship/book
    Note over FE,Session: Payload: { learnerId, creatorId, date, timeSlot }
    Session->>DB: Insert DoubtSession (booking_status = SCHEDULED)
    Session-->>FE: Return Session Details with Jitsi Room Link

    Note over Learner,Creator: Both Learner & Creator see session in Doubt Sessions tab
    Learner->>FE: Click "Join Call"
    FE->>FE: Open JitsiCallPage (Embedded 8x8 / Jitsi Meet SDK)
```

---

### Workflow 4: In-Reader Doubts & Q&A Discussion Forum

1. **Learner Question**:
   - Opens document in `UnifiedContentViewerPage.jsx`.
   - Opens **Doubts & Q&A** side drawer.
   - Submits technical question (`POST /api/qa/question`).
2. **Creator Response**:
   - Creator opens **Management Grid** in Creator Mode.
   - Clicks **💬 Q&A** button next to their uploaded content.
   - Replies to student doubt (`POST /api/qa/thread/{id}/reply`).
3. **Resolution**:
   - Reply is saved with `role = "CREATOR"`.
   - Displays a **Verified Answer** badge and marks thread as **Resolved** (`isResolved = true`).

---

### Workflow 5: Interactive Star Rating & Review System

1. **Submission**: Learner clicks **⭐ Rate Resource** inside Content Reader (`ReviewModal.jsx`).
2. **API Request**: Calls `POST /api/contents/{contentId}/reviews` with rating (1–5 stars) & review text.
3. **Backend Processing**:
   - Inserts record in `reviews` table.
   - Recalculates average rating and review count.
   - Updates `rating` & `reviews_count` on `contents` table in PostgreSQL.
4. **Marketplace Update**: Card reflects updated star rating dynamically.

---

## 🛠️ API Reference Cheat Sheet

| Feature | HTTP Method | Endpoint | Access Rule |
|---|---|---|---|
| **User Login** | `POST` | `/api/auth/login` | Public |
| **User Register** | `POST` | `/api/auth/register` | Public |
| **Marketplace Catalog** | `GET` | `/api/public/contents` | Public |
| **Resource Details** | `GET` | `/api/public/resource/{id}` | Public |
| **Upload Resource** | `POST` | `/api/creator/content` | Creator |
| **Manage Content Status** | `PUT` | `/api/creator/content/{id}/status` | Creator |
| **Buy Resource** | `POST` | `/api/purchases/buy` | Learner |
| **Learner Library** | `GET` | `/api/user/library` | Learner |
| **Book Doubt Session** | `POST` | `/api/mentorship/book` | Authenticated |
| **Get Doubt Sessions** | `GET` | `/api/mentorship/sessions` | Authenticated |
| **Q&A Threads** | `GET` | `/api/qa/content/{contentId}` | Authenticated |
| **Post Question** | `POST` | `/api/qa/question` | Authenticated |
| **Post Reply** | `POST` | `/api/qa/thread/{id}/reply` | Authenticated |
| **Submit Review** | `POST` | `/api/contents/{contentId}/reviews` | Authenticated |
| **Toggle User Freeze** | `PUT` | `/api/admin/users/{id}/freeze` | Admin |

---

## 🚀 How to Run the Complete Stack

### 1. Backend Setup (`project-backend-Java`)
```powershell
# Ensure PostgreSQL database 'learnhub_db' exists on localhost:5432
cd C:\Users\shubh\Desktop\Projects\project-backend-Java

# Compile and start Spring Boot Application
.\mvnw spring-boot:run
```
*Backend runs on **`http://localhost:8080`***

### 2. Frontend Setup (`project-frontend-react`)
```powershell
cd C:\Users\shubh\Desktop\Projects\CDAC-Final-Project\project-frontend-react

# Install dependencies (if needed)
npm install

# Start Vite Development Server
npm run dev
```
*Frontend runs on **`http://localhost:5173`***
