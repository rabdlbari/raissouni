package com.bookshop.controller;

import com.bookshop.entity.User;
import com.bookshop.service.UserService;
import com.bookshop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/simple-login")
    public String simpleLogin(@RequestBody User user) {
        if ("user1@example.com".equals(user.getEmail()) && "pass123".equals(user.getPassword())) {
            return "Login successful!";
        } else {
            return "Login failed!";
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        return jwtUtil.generateToken(auth.getName());
    }
}