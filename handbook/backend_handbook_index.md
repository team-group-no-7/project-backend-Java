# 📘 LearnHub Backend Development Handbook — Master Index

> **Project:** LearnHub — Learner-focused Content Platform with Monetization and Interaction
> **Stack:** Spring Boot 4.1.0 · Java 17 · PostgreSQL 15 · Spring Security · JWT · Lombok · Maven
> **Architecture:** Modular Monolith (Vertical Domain Packages)
> **Team:** Yashwant · Riya · Sakshi · Shubham

---

## 📖 How to Use This Handbook

This handbook is the **single source of truth** for the entire LearnHub backend development lifecycle. It is divided into 4 parts covering 16+ sections. Every team member should read **Part 1** and **Part 2** completely before starting any development work, then reference Parts 3 and 4 as needed during implementation.

---

## 📑 Table of Contents

### [Part 1: Architecture, Folder Structure & Module Ownership](file:///C:/Users/Yashwant%20Pandey/.gemini/antigravity-cli/brain/e3256d05-57e8-4419-8061-21bddf5ed439/backend_handbook_part1_architecture.md)

| Section | Topic | Key Contents |
|:---|:---|:---|
| **§1** | Project Architecture | Modular Monolith, Horizontal vs Vertical architecture, Domain ownership, Layer responsibilities (Controller→Service→Repository→Entity→DTO), Future Microservice extraction, Why roles ≠ packages |
| **§2** | Backend Folder Structure | Package-by-package breakdown (common, auth, user, catalog, billing, discussion, mentorship), Naming conventions, Annotations guide |
| **§3** | Module Ownership | Per-module: Responsibilities, Database tables, Entities (current vs planned), Repositories, Services, Controllers, Future APIs, Microservice mapping |

---

### [Part 2: Team Development Plan, Git Workflow & Feature Ownership](file:///C:/Users/Yashwant%20Pandey/.gemini/antigravity-cli/brain/e3256d05-57e8-4419-8061-21bddf5ed439/backend_handbook_part2_team_and_git.md)

| Section | Topic | Key Contents |
|:---|:---|:---|
| **§4** | Team Development Plan | Per-member (Yashwant/Riya/Sakshi/Shubham): Responsibilities, Modules touched, Files to create, Files NOT to modify, Git branches, Expected commits, PRs, Dependencies, Integration sequence |
| **§5** | Detailed Git Workflow | Clone → Branch → Commit → Push → PR → Review → Merge → Release → Tagging. Complete commands, Mermaid gitgraph diagram, Conflict resolution, Naming conventions |
| **§15A** | Feature Ownership Matrix | Comprehensive table: Feature → Owner → Package → Controller → Entity → Endpoints → Frontend consumer → Dependencies → Git branch |

---

### [Part 3: Implementation Roadmap & API Documentation](file:///C:/Users/Yashwant%20Pandey/.gemini/antigravity-cli/brain/e3256d05-57e8-4419-8061-21bddf5ed439/backend_handbook_part3_implementation_roadmap.md)

| Section | Topic | Key Contents |
|:---|:---|:---|
| **§6** | Implementation Roadmap | 15-step feature lifecycle (DB → Entity → Repo → DTO → Service → Controller → Validation → Security → Exception → Testing → Frontend → Git). Per-team-member deep-dive with code examples |
| **§7** | API Documentation | Every endpoint: Method, URL, Purpose, Request/Response JSON, Validation, Authorization, Error responses. Organized by module (Auth, Catalog, Users, Billing, Mentorship, Discussion, Admin) |

---

### [Part 4: Database, Study Guide, Standards & Interview Prep](file:///C:/Users/Yashwant%20Pandey/.gemini/antigravity-cli/brain/e3256d05-57e8-4419-8061-21bddf5ed439/backend_handbook_part4_database_study_standards.md)

| Section | Topic | Key Contents |
|:---|:---|:---|
| **§8** | Database Documentation | Complete Mermaid ER diagram, All 9 tables with columns/types/constraints, Foreign key strategies (CASCADE vs SET NULL), Cross-module ID references, Index recommendations |
| **§9** | Study Roadmap | Per-member study plan: Topics, Estimated hours, Priority, Difficulty, Interview questions, Recommended resources |
| **§10** | Developer Learning Mode | 10 key Spring Boot patterns explained: Why they exist, Alternatives, Beginner mistakes, Interview talking points |
| **§11** | Implementation Checklists | Developer checklist, Reviewer checklist, Testing checklist, Deployment checklist |
| **§12** | Common Mistakes | Spring Boot (10+), Git (10+), JWT (5+), JPA (10+), Database (5+), Merge (5+), Architecture (5+) mistakes with correct approaches |
| **§13** | Code Standards | Naming conventions, Package/Class/Method/Variable rules, Controller/DTO/Entity/Repository conventions, REST API naming, Exception handling patterns, Logging, Comments |
| **§14** | Integration Guide | 4-developer conflict prevention, Dependency order, Feature merge sequence, CORS configuration, Frontend API consumption |
| **§15** | Interview Preparation | Per-feature: Architecture decisions, 5+ interviewer questions, Expected answers, Tradeoffs, Microservice migration discussion |
| **§16** | Final Completion Roadmap | 5-week Gantt chart: Week-by-week execution plan per team member, Integration milestones, Testing milestones, Merge order |

---

## 🏗️ Current Backend Implementation Status

| Module | Status | What Exists | What Needs to Be Built |
|:---|:---|:---|:---|
| **common** | 🟡 Scaffold | `config/`, `dto/`, `exception/`, `util/` package-info stubs | SecurityConfig, CorsConfig, GlobalExceptionHandler, JwtUtil, ApiResponse |
| **auth** | 🟡 Partial | `RefreshToken` entity, `AuthService`, `AuthController` (status + tokens) | Login, Register, JWT filter, token refresh, logout |
| **user** | 🟡 Partial | `User` entity, `UserRepository`, `UserService`, `UserController` (status + getAllUsers) | Profile CRUD, creator stats, admin user management |
| **catalog** | 🟠 Minimal | `Category` entity, `CatalogController` (status only) | `Content` entity, full CRUD, search/filter/sort, featured, marketplace |
| **billing** | 🔴 Stub | `BillingController` (status only) | `Purchase` entity, checkout, Razorpay mock, transaction history |
| **discussion** | 🔴 Stub | `DiscussionController` (status only) | `Question`, `DiscussionReply` entities, Q&A thread APIs |
| **mentorship** | 🔴 Stub | `MentorshipController` (status only) | `DoubtSession` entity, booking, Jitsi room generation |

---

## 👥 Team Quick Reference

| Team Member | Primary Responsibility | Primary Packages | First Branch |
|:---|:---|:---|:---|
| **Yashwant** | Auth + Creator features | `auth/`, `catalog/`, `user/` | `feature/auth` |
| **Riya** | Learner features | `user/`, `billing/`, `mentorship/` | `feature/learner-profile` |
| **Sakshi** | Public pages + Payments | `catalog/`, `billing/` | `feature/landing-page` |
| **Shubham** | Marketplace + Admin + Sessions | `catalog/`, `user/`, `mentorship/` | `feature/marketplace` |

---

> [!IMPORTANT]
> **Read Part 1 and Part 2 completely before writing any code.** These establish the architectural rules and team boundaries that prevent conflicts and ensure consistency.
