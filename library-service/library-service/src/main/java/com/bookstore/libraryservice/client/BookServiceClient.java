package com.bookstore.libraryservice.client;

import com.bookstore.libraryservice.dto.BookDto;
import com.bookstore.libraryservice.dto.BookIdDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "book-service", path = "/book")
public interface BookServiceClient {
    // NOTE : If there is no isbn, it creates a book with the default isbn
    @GetMapping("/isbn/{isbn}")
    @CircuitBreaker(name = "getBookByIsbnCircuitBreaker", fallbackMethod = "getBookFallback")
    ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable("isbn") String isbn);

    default ResponseEntity<BookIdDto> getBookFallback(String isbn, Exception exception) {
        return ResponseEntity.ok(new BookIdDto(-1L, "default-isbn"));
    }

    @GetMapping("/{book-id}")
    @CircuitBreaker(name = "getBookByIdCircuitBreaker", fallbackMethod = "getBookByIdFallback")
    BookDto getBookById(@PathVariable("book-id") Long id);

    default BookDto getBookByIdFallback(Long id, Exception exception) {
        return new BookDto(0L, null, "default-book", 0,"default","");
    }
}