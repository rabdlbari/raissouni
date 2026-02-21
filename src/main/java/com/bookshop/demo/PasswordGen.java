package com.bookshop.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGen {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "pass123";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + encodedPassword);
    }
}