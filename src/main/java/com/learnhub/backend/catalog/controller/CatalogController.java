package com.learnhub.backend.catalog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * CatalogController — Placeholder endpoint for Resource Catalog & Content Studio Module.
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @GetMapping("/status")
    public String getStatus() {
        return "Catalog & Content Studio Module is Active";
    }
}
