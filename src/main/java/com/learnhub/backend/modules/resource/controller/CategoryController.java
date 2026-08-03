package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.modules.resource.entity.Category;
import com.learnhub.backend.modules.resource.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * CategoryController — Exposes REST endpoints for categories management.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CatalogService catalogService;

    // GET /api/categories - Retrieve all categories for catalog chips/navigation
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = catalogService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
