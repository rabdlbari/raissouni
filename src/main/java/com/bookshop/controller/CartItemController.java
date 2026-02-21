package com.bookshop.controller;

import com.bookshop.dto.CartItemDTO;
import com.bookshop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartService;

    @Autowired
    public CartItemController(CartItemService cartService) {
        this.cartService = cartService;
    }

    // Get all items for a user
    @GetMapping("/{userId}")
    public List<CartItemDTO> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItemsByUser(userId);
    }

    // Add or update an item
    @PostMapping
    public CartItemDTO addCartItem(@RequestBody CartItemDTO dto) {
        return cartService.addOrUpdateCartItem(dto);
    }

    // Delete an item
    @DeleteMapping("/{userId}/{bookId}")
    public void deleteCartItem(@PathVariable Long userId, @PathVariable Long bookId) {
        cartService.deleteCartItem(userId, bookId);
    }
}