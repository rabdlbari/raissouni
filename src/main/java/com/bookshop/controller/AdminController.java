package com.bookshop.controller;


import com.bookshop.dto.BookDTO;
import com.bookshop.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private BookServiceImpl bookService;

    @PostMapping("/books")
    public BookDTO addBook(@RequestBody BookDTO dto){
        return bookService.saveBook(dto);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }
}
