package com.bookshop.controller;

import com.bookshop.entity.Book;
import com.bookshop.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@Tag(name = "Public Book API")
public class PublicBookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    @Operation(summary = "Get all books with pagination")
    public ResponseEntity<Page<Book>> getBooks(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Items per page") @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/books/{id}")
    @Operation(summary = "Get book details by ID")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}