package com.learnhub.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * WebConfig — Configures static resource serving for uploaded files (PDFs, images etc.)
 * Maps the local uploads/ directory to a public URL path so the React frontend can fetch PDFs.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files from uploads/pdfs/ directory at /uploads/pdfs/** URL
        Path uploadDir = Paths.get("uploads/pdfs").toAbsolutePath();
        String uploadPath = uploadDir.toUri().toString();

        registry.addResourceHandler("/uploads/pdfs/**")
                .addResourceLocations(uploadPath + "/")
                .setCachePeriod(3600); // 1 hour cache
    }
}
