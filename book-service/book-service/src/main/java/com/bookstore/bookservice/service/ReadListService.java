package com.bookstore.bookservice.service;

import com.bookstore.bookservice.dto.BookDto;


import java.util.List;

public interface ReadListService {
    void addBookToReadListByIsbn(String isbn);
    List<BookDto> getAllBooksFromReadList();
    void deleteFromReadListByIsbn(String isbn);
}
