package com.bookshop.dto;

public record BookDTO(
    Long id,
    String title,
    String author,
    Float price,
    Integer stock,
    String description,
    Long categoryId,
    String categoryName
) {}