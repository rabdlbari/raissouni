package com.bookshop.raissouni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.bookshop")
@EntityScan(basePackages = "com.bookshop.entity")
@EnableJpaRepositories(basePackages = "com.bookshop.repository")
public class RaissouniApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaissouniApplication.class, args);
	}

}
