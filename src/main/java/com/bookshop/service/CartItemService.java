package com.bookshop.service;

import com.bookshop.dto.CartItemDTO;
import com.bookshop.entity.Book;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.User;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CartItemRepository;
import com.bookshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepo,
                           UserRepository userRepo,
                           BookRepository bookRepo) {
        this.cartItemRepo = cartItemRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    // Get all cart items for a user
    public List<CartItemDTO> getCartItemsByUser(Long userId) {
        return cartItemRepo.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Add or update cart item
    @Transactional
    public CartItemDTO addOrUpdateCartItem(CartItemDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepo.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartItem cartItem = cartItemRepo.findByUserIdAndBookId(dto.getUserId(), dto.getBookId())
                .orElse(new CartItem());

        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setUnitPrice(dto.getUnitPrice());

        cartItemRepo.save(cartItem);

        return toDTO(cartItem);
    }

    // Delete cart item
    public void deleteCartItem(Long userId, Long bookId) {
        cartItemRepo.findByUserIdAndBookId(userId, bookId)
                .ifPresent(cartItemRepo::delete);
    }

    // Convert entity to DTO
    private CartItemDTO toDTO(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getUser().getId(),
                cartItem.getBook().getId(),
                cartItem.getQuantity(),
                cartItem.getUnitPrice()
        );
    }
}