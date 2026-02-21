package com.bookshop.controller;

import com.bookshop.dto.CartItemDTO;
import com.bookshop.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Shopping Cart")
public class CartItemController {

    private final CartItemService cartService;

    public CartItemController(CartItemService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Get all items in the shopping cart")
    public List<CartItemDTO> getCart(Authentication authentication) {
        String email = authentication.getName();
        return cartService.getCartItems(email);
    }

    @PostMapping("/items")
    @Operation(
            summary = "Add a book to the shopping cart",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartItemDTO.class),
                            examples = @ExampleObject(value = "{\"bookId\": 1, \"quantity\": 1}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item added successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public CartItemDTO addItem(@RequestBody CartItemDTO dto, Authentication authentication) {
        String email = authentication.getName();
        return cartService.addItem(email, dto);
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update item quantity in cart")
    public CartItemDTO updateItem(@PathVariable Long itemId,
                                  @RequestBody CartItemDTO dto,
                                  Authentication authentication) {
        String email = authentication.getName();
        return cartService.updateItem(email, itemId, dto.getQuantity());
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remove an item from the cart")
    public void deleteItem(@PathVariable Long itemId, Authentication authentication) {
        String email = authentication.getName();
        cartService.deleteItem(email, itemId);
    }
}