package com.bookstore.libraryservice.exception;

import org.springframework.http.HttpStatus;

public class BookAlreadyExistInLibraryException extends GenericException {
    public BookAlreadyExistInLibraryException(String message){
        super(HttpStatus.BAD_REQUEST.toString(), message, 400);
    }
}
