package com.bookstore.libraryservice.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    Long id;
    String image;
    String name;
    int bookYear;
    String style;
    String detail;
}
