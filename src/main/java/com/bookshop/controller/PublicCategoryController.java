package com.bookshop.controller;

import com.bookshop.entity.Category;
import com.bookshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@Tag(name = "Public Category API")
public class PublicCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    @Operation(summary = "Get all available book categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}