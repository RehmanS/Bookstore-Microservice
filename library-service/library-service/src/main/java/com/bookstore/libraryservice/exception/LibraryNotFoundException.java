package com.bookstore.libraryservice.exception;

import org.springframework.http.HttpStatus;

public class LibraryNotFoundException extends GenericException {
    public LibraryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.toString(), message, 404);
    }
}
