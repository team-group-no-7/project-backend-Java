package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.resource.dto.response.CategoryResponse;
import com.learnhub.backend.modules.resource.entity.Category;
import com.learnhub.backend.modules.resource.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryController — Exposes REST endpoints for categories management using CategoryResponse DTOs.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CatalogService catalogService;

    public CategoryController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /** GET /api/categories - Retrieve all categories for catalog chips/navigation */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<Category> categories = catalogService.getAllCategories();
        List<CategoryResponse> dtoList = categories.stream()
                .map(c -> new CategoryResponse(
                        c.getId(),
                        c.getName(),
                        c.getResourceCount() != null ? c.getResourceCount() : 0
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", dtoList));
    }
}
