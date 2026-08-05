# ====================================================================
# LearnHub Backend Dockerfile (Spring Boot 4.1.0 + OpenJDK 17)
# Simple & Beginner-Friendly Configuration for CDAC Project Viva
# ====================================================================

# Step 1: Use official JDK 17 base image (Eclipse Temurin Alpine for lightweight footprint)
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy Maven wrapper files and pom.xml first for dependency resolution
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Step 4: Grant execution permission to Maven wrapper script
RUN chmod +x mvnw

# Step 5: Copy application source code and resources into container
COPY src ./src

# Step 6: Expose port 8080 (Default Spring Boot HTTP Port)
EXPOSE 8080

# Step 7: Launch Spring Boot application using Maven wrapper
CMD ["./mvnw", "spring-boot:run"]
