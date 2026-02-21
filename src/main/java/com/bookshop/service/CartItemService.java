package com.bookshop.service;

import com.bookshop.dto.CartItemDTO;
import com.bookshop.entity.Book;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.User;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CartItemRepository;
import com.bookshop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;

    public CartItemService(CartItemRepository cartItemRepo,
                           UserRepository userRepo,
                           BookRepository bookRepo) {
        this.cartItemRepo = cartItemRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    // GET CART ITEMS
    public List<CartItemDTO> getCartItems(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartItemRepo.findByUserId(user.getId())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ADD ITEM
    @Transactional
    public CartItemDTO addItem(String email, CartItemDTO dto) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepo.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (dto.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }

        if (book.getStock() < dto.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }

        CartItem cartItem = cartItemRepo
                .findByUserIdAndBookId(user.getId(), book.getId())
                .orElse(new CartItem());

        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setUnitPrice(book.getPrice());

        cartItemRepo.save(cartItem);

        return toDTO(cartItem);
    }

    // UPDATE ITEM QUANTITY
    @Transactional
    public CartItemDTO updateItem(String email, Long itemId, Integer quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartItem cartItem = cartItemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);

        return toDTO(cartItem);
    }

    // DELETE ITEM
    @Transactional
    public void deleteItem(String email, Long itemId) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartItem cartItem = cartItemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }
        cartItemRepo.delete(cartItem);
    }

    // ENTITY → DTO
    private CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setBookId(cartItem.getBook().getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setUnitPrice(cartItem.getUnitPrice());
        return dto;
    }
}