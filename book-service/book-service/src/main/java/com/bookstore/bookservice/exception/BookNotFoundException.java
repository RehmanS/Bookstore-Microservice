package com.bookstore.bookservice.exception;

import org.springframework.http.HttpStatus;

// @ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends GenericException{
    public BookNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.toString(), message, 404);
    }
}
