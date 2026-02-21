package com.bookshop.repository;

import com.bookshop.entity.Book;
import com.bookshop.entity.Category; 
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.bookshop.raissouni.RaissouniApplication.class)
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSaveAndFindById_ShouldReturnBook() {

        Category category = new Category();
        category.setName("Informatique"); 
        entityManager.persist(category); 

        // 2. Création du livre
        Book book = new Book();
        book.setTitle("Spring Boot Avancé");
        book.setAuthor("Jane Doe");
        book.setPrice(new java.math.BigDecimal("45.00"));
        book.setStock(15);
        book.setCategory(category); 
        
        Book savedBook = bookRepository.save(book);
        Long generatedId = savedBook.getId();

        Optional<Book> foundBook = bookRepository.findById(generatedId);

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Spring Boot Avancé");
    }
}