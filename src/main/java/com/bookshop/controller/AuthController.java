package com.bookshop.controller;

import com.bookshop.entity.User;
import com.bookshop.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login to the system",
            description = "Authenticate user and return JWT token",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    value = "{\"email\": \"admin@example.com\", \"passwordHash\": \"123456\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, token generated"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    public String login(@org.springframework.web.bind.annotation.RequestBody User user) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return jwtService.generateToken(userDetails);

        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/simple-login")
    @Operation(summary = "Internal test login", hidden = true)
    public String simpleLogin(@org.springframework.web.bind.annotation.RequestBody User user) {
        if ("user1@example.com".equals(user.getEmail()) && "pass123".equals(user.getPassword())) {
            return "Login successful!";
        } else {
            return "Login failed!";
        }
    }
}