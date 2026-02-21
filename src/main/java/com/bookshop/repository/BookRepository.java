package com.bookshop.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.bookshop.entity.Book; 

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Pagination is required for the public API
    Page<Book> findAll(Pageable pageable); 
    Page<Book> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}