# 🚀 LearnHub Java Backend Setup & Run Guide

Welcome to the **LearnHub** Spring Boot API project. This backend is structured as a **Modular Monolith** (vertical domain package design) so that modules can easily be converted into microservices later.

---

## 🛠️ Prerequisites
Ensure you have the following installed locally:
* **Java Development Kit (JDK)**: Version 17 or higher.
* **Database**: PostgreSQL (either locally installed or running via Docker).
* **IDE**: IntelliJ IDEA (recommended), Eclipse, or VS Code.
* **REST Client**: Postman (for testing endpoints).

---

## 🗄️ Database Setup

### Option A: Running PostgreSQL inside a Docker Container (Recommended)
If you have Docker Desktop installed, navigate to the root folder of the backend project and run:
```bash
docker compose up -d
```
*This downloads PostgreSQL, maps it to port `5432`, sets the default credentials to `postgres/postgres`, and initializes the database `learnhub_db` automatically.*

### Option B: Local PostgreSQL Installation
If you do not have Docker installed:
1. Open pgAdmin 4 or the `psql` shell tool.
2. Create an empty database named `learnhub_db`:
   ```sql
   CREATE DATABASE learnhub_db;
   ```
3. If your local Postgres uses a password other than `postgres`, configure it by setting an environment variable before launching (so you don't modify the committed properties file):
   * **Windows (PowerShell)**:
     ```powershell
     $env:SPRING_DATASOURCE_PASSWORD="your_custom_password"
     ```
   * **macOS / Linux**:
     ```bash
     export SPRING_DATASOURCE_PASSWORD="your_custom_password"
     ```

---

## 🏁 Running the Application

Open a terminal in the root of the Java backend directory and execute:

* **Windows**:
  ```powershell
  .\mvnw spring-boot:run
  ```
* **macOS / Linux**:
  ```bash
  ./mvnw spring-boot:run
  ```

Upon startup, **Hibernate** will auto-create the database tables matching the schema, and [data.sql](src/main/resources/data.sql) will seed the lookup database entries automatically.

---

## 📡 Testing the API Endpoints

Because Spring Security is active, all requests must be authenticated.

### 🔑 Credentials (Local Dev Setup)
* **Default Username**: `admin`
* **Default Password**: `admin123`

### 🧪 Testing in the Browser
Open your browser and navigate to one of the domain module status endpoints. The browser will prompt you for a username and password. Enter `admin` / `admin123`:

* **Authentication Module**: [http://localhost:8080/api/auth/status](http://localhost:8080/api/auth/status)
* **User Profile Module**: [http://localhost:8080/api/users/status](http://localhost:8080/api/users/status)
* **Content Catalog Module**: [http://localhost:8080/api/catalog/status](http://localhost:8080/api/catalog/status)
* **Billing Module**: [http://localhost:8080/api/billing/status](http://localhost:8080/api/billing/status)
* **Mentorship Module**: [http://localhost:8080/api/mentorship/status](http://localhost:8080/api/mentorship/status)
* **Discussion Module**: [http://localhost:8080/api/discussion/status](http://localhost:8080/api/discussion/status)

### 📬 Testing in Postman
1. Create a new request in Postman.
2. Go to the **Authorization** tab.
3. Select **Basic Auth** from the dropdown menu.
4. Input Username: `admin` and Password: `admin123`.
5. Enter the target endpoint URL and click **Send**.
