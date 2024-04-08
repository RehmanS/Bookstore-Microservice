package com.bookstore.libraryservice.exception;

import lombok.Data;

@Data
public class GenericException extends RuntimeException{
    private final String code;
    private final String message;
    private final int status;

    public GenericException(String code, String message, int status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s{status=%d, code='%s', message='%s'}",
                this.getClass().getSimpleName(), status, code, message);
    }
}
