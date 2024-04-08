package com.bookstore.bookservice.dto;

import lombok.Builder;

@Builder
public record BookResponse(
        Long id,
        String image,
        String name,
        String author,
        int bookYear,
        String isbn,
        String fiction,
        String detail) {
}
