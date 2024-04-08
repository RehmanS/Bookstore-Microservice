package com.bookstore.libraryservice.dto;

import lombok.Builder;

@Builder
public record LibraryResponse(
        Long id,
        String libraryName
) {
}
