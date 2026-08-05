# 🚀 LearnHub — Project Setup & Local Developer Onboarding Guide

This guide provides step-by-step instructions for setting up and running **LearnHub** (Java Spring Boot Backend + React Frontend + PostgreSQL Database) on your local development environment.

---

## 🛠️ Step 1: Prerequisites Verification

Ensure you have the following software installed on your local machine:

1. **Java Development Kit (JDK)**: Version 17 or higher (`java -version`).
2. **Node.js & npm**: Version 18 or higher (`node -v`).
3. **PostgreSQL Database**: Version 14 or higher (`psql --version`).
4. **Git**: Version control client.

---

## 📥 Step 2: Clone the Repositories

```bash
# Clone Backend Repository
git clone https://github.com/team-group-no-7/project-backend-Java.git

# Clone Frontend Repository
git clone https://github.com/team-group-no-7/project-frontend-react.git
```

---

## 🗄️ Step 3: Database Creation

Open PostgreSQL using `psql` command line or **pgAdmin**:

```sql
CREATE DATABASE learnhub_db;
```

---

## ⚙️ Step 4: Automatic Database Initialization Logic

No manual SQL execution is required! The backend application properties configure automated execution:

* **Schema Execution**: `spring.sql.init.mode=always` automatically runs [`schema.sql`](src/main/resources/schema.sql) to create all 9 core PostgreSQL tables (`users`, `categories`, `contents`, `purchases`, `questions`, `discussion_replies`, `doubt_sessions`, `reviews`, `refresh_tokens`).
* **Data Seeding Execution**: `spring.jpa.defer-datasource-initialization=true` automatically runs [`data.sql`](src/main/resources/data.sql) after schema creation, populating seed users, categories, study materials, student reviews, Q&A threads, and mentorship sessions using `ON CONFLICT (id) DO NOTHING`.
* **DDL Auto**: `spring.jpa.hibernate.ddl-auto=update` verifies JPA entity definitions against database tables.

---

## 🔧 Step 5: Application Properties & Custom Credentials

In [`src/main/resources/application.properties`](src/main/resources/application.properties), the default connection settings are:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/learnhub_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

If your local PostgreSQL installation uses a custom password, set environment variables in your terminal before launching Spring Boot (without modifying tracked code):

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

*The Spring Boot server will start at `http://localhost:8080`.*

---

## ⚛️ Step 7: Running the React Frontend

Open a second terminal in the `project-frontend-react` root directory and execute:

```bash
# Install dependencies
npm install

# Start Vite development server
npm run dev
```

*The React application will start at `http://localhost:5173`.*

---

## 📡 Step 8: Swagger API Documentation

Once the backend is running, inspect and test REST endpoints using Swagger UI:

* **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **OpenAPI Specs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔑 Step 9: Default Accounts for Local Testing

Verified from [`data.sql`](src/main/resources/data.sql):

| Role | Email | Password | Privileges |
| :--- | :--- | :--- | :--- |
| **Learner** | `arjun.mehta@learnhub.com` | `password123` | Marketplace, Purchase, PDF Reader, Q&A |
| **Learner** | `priya.sharma@learnhub.com` | `password123` | Marketplace, Reviews, Q&A, Doubt Sessions |
| **Creator** | `rohan.verma@learnhub.com` | `password123` | Content Studio, Management Grid, Q&A Replies |
| **Admin** | `admin@learnhub.com` | `admin123` | Admin Panel, User/Resource/Txn Grids, Refunds |

---

## ❓ Step 10: Troubleshooting

* **Problem: PostgreSQL Connection Refused (`PSQLException: Connection refused`)**
  * *Solution*: Ensure PostgreSQL service is running on port 5432 and database `learnhub_db` exists.
* **Problem: Port 8080 / 5173 in use**
  * *Solution*: Stop any process using port 8080 (`netstat -ano | findstr 8080`) or port 5173.
