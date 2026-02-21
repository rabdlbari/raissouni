package com.bookshop.service.impl;

import com.bookshop.dto.BookDTO;
import com.bookshop.entity.Book;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
    }

    public BookDTO saveBook(BookDTO dto) {

        Book book;

        if (dto.id() != null) {
            book = bookRepository.findById(dto.id())
                    .orElseThrow(() -> new RuntimeException("Book not found: " + dto.id()));
        } else {
            book = new Book();
        }

        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setDescription(dto.description());
        book.setPrice(BigDecimal.valueOf(dto.price()));
        book.setStock(dto.stock());

        book.setCategory(
                categoryRepository.findById(dto.categoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found: " + dto.categoryId()))
        );

        Book saved = bookRepository.save(book);

        return toDTO(saved);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    private BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice().floatValue(),
                book.getStock(),
                book.getDescription(),
                book.getCategory().getId(),
                book.getCategory().getName()
        );
    }
}