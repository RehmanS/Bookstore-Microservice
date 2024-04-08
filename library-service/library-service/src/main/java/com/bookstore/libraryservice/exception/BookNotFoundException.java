package com.bookstore.libraryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends GenericException {
    public BookNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.toString(), message, 404);
    }
}
