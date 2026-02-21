package com.bookshop.service;

import com.bookshop.entity.Category;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void getAllCategories_shouldReturnList() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Fiction");

        Category c2 = new Category();
        c2.setId(2L);
        c2.setName("Science");

        when(categoryRepository.findAll()).thenReturn(List.of(c1, c2));

        List<Category> categories = categoryService.getAllCategories();

        assertEquals(2, categories.size());
        assertEquals("Fiction", categories.get(0).getName());
        assertEquals("Science", categories.get(1).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_emptyList_shouldReturnEmpty() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<Category> categories = categoryService.getAllCategories();

        assertTrue(categories.isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_verifyRepositoryCall() {
        categoryService.getAllCategories();
        verify(categoryRepository, times(1)).findAll();
    }
}