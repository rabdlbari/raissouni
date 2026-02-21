package com.bookshop.controller;

import com.bookshop.entity.User;
import com.bookshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current profile")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserById(((com.bookshop.entity.User) userDetails).getId());
    }

    @GetMapping
    @Operation(summary = "List all users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Create a new user by providing email, password, and role",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    value = "{\"email\": \"user@example.com\", \"passwordHash\": \"123456\", \"role\": \"ROLE_USER\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Input")
            }
    )
    public User registerUser(@org.springframework.web.bind.annotation.RequestBody User user) {
        return userService.createUser(user);
    }
}