package com.bookstore.libraryservice.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundByIdException extends GenericException {
    public BookNotFoundByIdException(String message) {
        super(HttpStatus.NOT_FOUND.toString(), message, 404);
    }
}
