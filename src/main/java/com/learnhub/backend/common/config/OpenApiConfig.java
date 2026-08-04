package com.learnhub.backend.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig
 *
 * Global Swagger / OpenAPI configuration for LearnHub.
 * This class configures API metadata and JWT authentication
 * for Swagger UI.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "LearnHub REST API",
                version = "v1.0.0",
                description = """
                        LearnHub is a learner-focused content platform with
                        monetization and interaction.

                        This API documentation provides all available REST
                        endpoints for authentication, user management,
                        content management, payments, mentorship,
                        discussions and administration.
                        """
        ),

        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Development Server"
                )
        },

        security = {
                @SecurityRequirement(name = "Bearer Authentication")
        }
)

@SecurityScheme(
        name = "Bearer Authentication",
        description = "Enter JWT Bearer Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
}