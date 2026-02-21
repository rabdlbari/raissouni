package com.bookshop.service;

import com.bookshop.dto.CartItemDTO;
import com.bookshop.entity.Book;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.User;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CartItemRepository;
import com.bookshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    private CartItemRepository cartRepo;
    private UserRepository userRepo;
    private BookRepository bookRepo;
    private CartItemService cartService;

    @BeforeEach
    void setUp() {
        cartRepo = mock(CartItemRepository.class);
        userRepo = mock(UserRepository.class);
        bookRepo = mock(BookRepository.class);
        cartService = new CartItemService(cartRepo, userRepo, bookRepo);
    }

    @Test
    void getCartItems_shouldReturnList() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setUser(user);
        cartItem.setQuantity(2);
        cartItem.setUnitPrice(new BigDecimal("10.00"));
        Book book = new Book();
        book.setId(1L);
        cartItem.setBook(book);

        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(cartRepo.findByUserId(1L)).thenReturn(List.of(cartItem));

        List<CartItemDTO> items = cartService.getCartItems("user@example.com");
        assertEquals(1, items.size());
        assertEquals(2, items.get(0).getQuantity());
    }

    @Test
    void addItem_shouldAddCartItem() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Book book = new Book();
        book.setId(1L);
        book.setPrice(new BigDecimal("20.00"));
        book.setStock(5);

        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(cartRepo.findByUserIdAndBookId(1L, 1L)).thenReturn(Optional.empty());

        CartItemDTO dto = new CartItemDTO(1L, 1L, 2, new BigDecimal("20.00"));
        CartItemDTO result = cartService.addItem("user@example.com", dto);

        assertEquals(2, result.getQuantity());
        verify(cartRepo, times(1)).save(any(CartItem.class));
    }

    @Test
    void addItem_invalidQuantity_shouldThrow() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Book book = new Book();
        book.setId(1L);
        book.setPrice(new BigDecimal("20.00"));
        book.setStock(5);

        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));

        CartItemDTO dto = new CartItemDTO(1L, 1L, 0, new BigDecimal("20.00"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cartService.addItem("user@example.com", dto));
        assertEquals("Quantity must be positive", ex.getMessage());
    }

    @Test
    void updateItem_shouldUpdateQuantity() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setUser(user);
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(new BigDecimal("20.00"));

        Book book = new Book();
        book.setId(1L);
        book.setPrice(new BigDecimal("20.00"));
        cartItem.setBook(book);

        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(cartRepo.findById(1L)).thenReturn(Optional.of(cartItem));

        CartItemDTO result = cartService.updateItem("user@example.com", 1L, 3);

        assertEquals(3, result.getQuantity());
        verify(cartRepo, times(1)).save(cartItem);
    }

    @Test
    void deleteItem_shouldDelete() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setUser(user);

        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(cartRepo.findById(1L)).thenReturn(Optional.of(cartItem));

        cartService.deleteItem("user@example.com", 1L);

        verify(cartRepo, times(1)).delete(cartItem);
    }
}