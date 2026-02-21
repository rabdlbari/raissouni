
package com.bookshop.service;

import com.bookshop.entity.Book;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        categoryRepository = mock(CategoryRepository.class); // mock the second dependency
        bookService = new BookServiceImpl(bookRepository, categoryRepository); // pass both
    }

    @Test
    void getAllBooks_shouldReturnPagedBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setPrice(new BigDecimal("10.00"));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setPrice(new BigDecimal("15.00"));

        Pageable pageable = PageRequest.of(0, 2);
        Page<Book> page = new PageImpl<>(List.of(book1, book2));

        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<Book> result = bookService.getAllBooks(pageable);
        assertEquals(2, result.getContent().size());
        assertEquals("Book 1", result.getContent().get(0).getTitle());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void getBookById_shouldReturnBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);
        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_notFound_shouldThrow() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookService.getBookById(1L));
        assertEquals("Book not found: 1", ex.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    // 4️⃣ Test pagination with empty page
    @Test
    void getAllBooks_emptyPage_shouldReturnEmptyList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> emptyPage = new PageImpl<>(List.of());

        when(bookRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<Book> result = bookService.getAllBooks(pageable);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void getAllBooks_multiplePages_shouldReturnCorrectPage() {
        Book book = new Book();
        book.setId(1L);

        Pageable pageable = PageRequest.of(1, 1); // page 1, size 1
        Page<Book> page = new PageImpl<>(List.of(book), pageable, 2);

        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<Book> result = bookService.getAllBooks(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals(2, result.getTotalElements()); // total elements across pages
    }
}