package com.bookstore.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookDto {

    Long id;
    String image;
    String name;
    String author;
    int bookYear;
    String isbn;
    String fiction;
    String detail;
}
