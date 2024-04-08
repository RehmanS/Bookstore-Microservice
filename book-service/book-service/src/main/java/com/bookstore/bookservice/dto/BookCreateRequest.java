package com.bookstore.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record BookCreateRequest(String image,
                                @NotBlank String name,
                                String author,
                                int bookYear,
                                String fiction,
                                String detail,
                                @NotBlank String isbn) {
}
