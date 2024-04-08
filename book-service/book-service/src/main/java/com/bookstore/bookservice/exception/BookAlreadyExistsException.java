package com.bookstore.bookservice.exception;

import org.springframework.http.HttpStatus;

public class BookAlreadyExistsException extends GenericException {
    public BookAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST.toString(), message, 400);
    }
}
