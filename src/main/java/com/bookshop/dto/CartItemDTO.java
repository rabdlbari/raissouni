package com.bookshop.dto;

import java.math.BigDecimal;

public class CartItemDTO {

    private Long userId;
    private Long bookId;
    private Integer quantity;
    private BigDecimal unitPrice;

    public CartItemDTO() {}

    public CartItemDTO(Long userId, Long bookId, Integer quantity, BigDecimal unitPrice) {
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}