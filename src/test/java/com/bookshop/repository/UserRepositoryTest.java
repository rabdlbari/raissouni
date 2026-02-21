package com.bookshop.repository;

import com.bookshop.entity.User;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {
        Optional<User> foundUser = userRepository.findByEmail("inconnu@examen.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    public void testFindByEmail_ShouldReturnUser_WhenUserExists() {
        User user = new User();
        user.setEmail("test@examen.com");
        
        user.setPasswordHash("SuperMotDePasse123!"); 
        
        user.setRole("USER"); 

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@examen.com");
        
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@examen.com");
    }
}