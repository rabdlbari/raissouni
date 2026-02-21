package com.bookshop.controller;

import com.bookshop.entity.User;
import com.bookshop.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            System.out.println("Attempting login for email: " + user.getEmail());
            System.out.println("Password received from Postman: " + user.getPassword());

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return jwtService.generateToken(userDetails);

        } catch (Exception e) {
            System.out.println("Login Failed! Reason: " + e.getMessage());
            throw e;
        }
    }

    @PostMapping("/simple-login")
    public String simpleLogin(@RequestBody User user) {
        if ("user1@example.com".equals(user.getEmail()) && "pass123".equals(user.getPassword())) {
            return "Login successful!";
        } else {
            return "Login failed!";
        }
    }
}