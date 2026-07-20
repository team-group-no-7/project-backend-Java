# 🎓 LearnHub Backend (Spring Boot & PostgreSQL)

> ⚠️ **Branching Rule**: Make changes to `dev` branch only. Do not commit to `main` branch directly.

---

### 📝 About the Project

LearnHub Backend is a robust RESTful web service built with **Spring Boot 4.1.0** and **PostgreSQL** to power a learner-focused content marketplace.
It handles user authentication (JWT), management, digital document purchases, 1-on-1 video doubt session scheduling, and community discussion forums.
Designed using a code-first Spring Data JPA architectural model, it enforces role-based access control (`LEARNER`, `CREATOR`, `ADMIN`) and secure file streaming.

---

### 🚀 How to Run

#### Prerequisites

- **Java 17** or higher installed (`java -version`)
- **PostgreSQL** database running with a database named `learnhub_db`
- Maven Wrapper (`mvnw` / `mvnw.cmd`) included in project

#### 1. Database Setup

Ensure PostgreSQL is running and update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/learnhub_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

#### 2. Build and Run Application

**Windows (CMD / PowerShell):**

```cmd
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

The Spring Boot backend server will start at **`http://localhost:8080`**.
