package com.bookshop.controller;

import com.bookshop.dto.CartItemDTO;
import com.bookshop.service.CartItemService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartService;

    public CartItemController(CartItemService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart
    @GetMapping
    public List<CartItemDTO> getCart(Authentication authentication) {
        String email = authentication.getName();
        return cartService.getCartItems(email);
    }

    // POST /api/cart/items
    @PostMapping("/items")
    public CartItemDTO addItem(@RequestBody CartItemDTO dto,
                               Authentication authentication) {
        String email = authentication.getName();
        return cartService.addItem(email, dto);
    }

    // PUT /api/cart/items/{itemId}
    @PutMapping("/items/{itemId}")
    public CartItemDTO updateItem(@PathVariable Long itemId,
                                  @RequestBody CartItemDTO dto,
                                  Authentication authentication) {
        String email = authentication.getName();
        return cartService.updateItem(email, itemId, dto.getQuantity());
    }

    // DELETE /api/cart/items/{itemId}
    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable Long itemId,
                           Authentication authentication) {
        String email = authentication.getName();
        cartService.deleteItem(email, itemId);
    }
}