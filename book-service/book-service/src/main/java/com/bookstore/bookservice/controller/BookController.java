package com.bookstore.bookservice.controller;

import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<String> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        bookService.createBook(bookCreateRequest);
        return ResponseEntity.ok("Book successfully created");
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{book-id}")
    public BookDto getBookById(@PathVariable("book-id") Long id) {
        return bookService.getBookByID(id);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable @NotEmpty String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @DeleteMapping({"/{isbn}"})
    public ResponseEntity<String> deleteBookByIsbn(@PathVariable("isbn") String isbn) {
        bookService.deleteBookByIsbn(isbn);
        return ResponseEntity.ok("Book successfully deleted by isbn: " + isbn);
    }

    @PutMapping
    public BookResponse updateBookByIsbn(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return bookService.updateBookByIsbn(bookCreateRequest);
    }

    @GetMapping("/name")
    public List<BookResponse> findBooksByName(@RequestParam("text") String text){
        return bookService.findBookByName(text);
    }

    @GetMapping("/author")
    public List<BookResponse> findBooksByAuthor(@RequestParam("text") String text){
        return bookService.findBookByAuthor(text);
    }


}

