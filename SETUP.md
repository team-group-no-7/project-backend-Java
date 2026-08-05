# 🚀 LearnHub Backend — Setup & Development Guide

This guide provides step-by-step instructions for setting up and running the **LearnHub Java Backend** (Spring Boot + PostgreSQL) on your local development machine.

---

## 🛠️ Step 1: Prerequisites Verification

Ensure you have the following software installed:

1. **Java Development Kit (JDK)**: Version 17 or higher (`java -version`).
2. **PostgreSQL Database**: Version 14 or higher (`psql --version`).
3. **Maven Wrapper**: Included in repository (`mvnw` / `mvnw.cmd`).
4. **Git**: Version control client.

---

## 📥 Step 2: Clone Backend Repository

```bash
git clone https://github.com/team-group-no-7/project-backend-Java.git
cd project-backend-Java
```

---

## 🗄️ Step 3: Database Creation

Open PostgreSQL using `psql` command line or **pgAdmin**:

```sql
CREATE DATABASE learnhub_db;
```

---

## ⚙️ Step 4: Automated Database Initialization

No manual SQL execution is required! The backend application properties configure automated execution:

* **Schema Execution**: `spring.sql.init.mode=always` automatically runs [`schema.sql`](src/main/resources/schema.sql) to create all 9 core PostgreSQL tables (`users`, `categories`, `contents`, `purchases`, `questions`, `discussion_replies`, `doubt_sessions`, `reviews`, `refresh_tokens`).
* **Data Seeding Execution**: `spring.jpa.defer-datasource-initialization=true` automatically runs [`data.sql`](src/main/resources/data.sql) after schema creation, populating seed users, categories, study materials, student reviews, Q&A threads, and mentorship sessions using `ON CONFLICT (id) DO NOTHING`.
* **DDL Auto**: `spring.jpa.hibernate.ddl-auto=update` verifies JPA entity definitions against database tables.

---

## 🔧 Step 5: Application Properties & Custom Credentials

In [`src/main/resources/application.properties`](src/main/resources/application.properties), default connection settings are:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/learnhub_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

If your local PostgreSQL installation uses a custom password, set environment variables in your terminal before launching Spring Boot (without modifying committed code):

* **Windows (PowerShell)**:
  ```powershell
  $env:SPRING_DATASOURCE_PASSWORD="your_custom_password"
  ```
* **macOS / Linux**:
  ```bash
  export SPRING_DATASOURCE_PASSWORD="your_custom_password"
  ```

---

## ☕ Step 6: Running the Java Backend

Open a terminal in the `project-backend-Java` root directory and execute:

* **Windows (PowerShell / CMD)**:
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
* **macOS / Linux**:
  ```bash
  ./mvnw spring-boot:run
  ```

*The backend REST API server will start at `http://localhost:8080`.*

---

## 📡 Step 7: Swagger API Documentation

Once the backend is running, inspect and test REST endpoints using Swagger UI:

* **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **OpenAPI Specs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔑 Step 8: Default Accounts for API Testing

Verified from [`data.sql`](src/main/resources/data.sql):

| Role | Email | Password | Privileges |
| :--- | :--- | :--- | :--- |
| **Learner** | `arjun.mehta@learnhub.com` | `password123` | Read APIs, Purchases, Q&A |
| **Learner** | `priya.sharma@learnhub.com` | `password123` | Read APIs, Reviews, Q&A, Mentorship |
| **Creator** | `rohan.verma@learnhub.com` | `password123` | Content CRUD, PDF Uploads, Q&A Replies |
| **Admin** | `admin@learnhub.com` | `admin123` | Admin REST APIs, Refund Actions |

---

## ❓ Step 9: Troubleshooting

* **Problem: PostgreSQL Connection Refused (`PSQLException: Connection refused`)**
  * *Solution*: Ensure PostgreSQL service is running on port 5432 and database `learnhub_db` exists.
* **Problem: Port 8080 in use**
  * *Solution*: Stop any process using port 8080 (`netstat -ano | findstr 8080`).
