package com.bookshop.service;

import com.bookshop.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

public interface BookService {
    Page<Book> getAllBooks(Pageable pageable);
    Book getBookById(Long id);
    Page<Book> getBooksByCategory(Long categoryId, Pageable pageable);
    Page<Book> getBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);  
}