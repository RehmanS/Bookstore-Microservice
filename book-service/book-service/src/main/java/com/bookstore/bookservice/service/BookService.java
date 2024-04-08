package com.bookstore.bookservice.service;

import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookIdDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.entity.Book;

import java.util.List;

public interface BookService {
    void createBook(BookCreateRequest bookCreateRequest);
    List<BookDto> getAllBooks();
    BookDto getBookByID(Long id);
    BookDto getBookByIsbn(String isbn);
    void deleteBookByIsbn(String isbn);
    BookResponse updateBookByIsbn(BookCreateRequest bookCreateRequest);
    List<BookResponse> findBookByName(String text);
    List<BookResponse> findBookByAuthor(String text);
}
