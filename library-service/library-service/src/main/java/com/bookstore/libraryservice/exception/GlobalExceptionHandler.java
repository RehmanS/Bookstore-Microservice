package com.bookstore.libraryservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(GenericException exception, WebRequest request) {

        String path = ((ServletWebRequest)request).getRequest().getRequestURL().toString();
        return createErrorResponse(exception,path);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                     WebRequest request) {
        var path = ((ServletWebRequest) request).getRequest().getRequestURL().toString();

        var build = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Input parameter is not valid!")
                .path(path)
                .timestamp(LocalTime.now())
                .build();
        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception,
                                                                 WebRequest request) {
        var path = ((ServletWebRequest) request).getRequest().getRequestURL().toString();

        var build = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Name or isbn can't be blank!")
                .path(path)
                .timestamp(LocalTime.now())
                .build();

        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request) {
        var path = ((ServletWebRequest) request).getRequest().getRequestURL().toString();

        var build = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Method Not Allowed: Make sure your request is correct")
                .path(path)
                .timestamp(LocalTime.now())
                .build();
        return ResponseEntity.badRequest().body(build);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(GenericException exception, String path) {
        var build = ErrorResponse.builder()
                .statusCode(exception.getStatus())
                .message(exception.getMessage())
                .path(path)
                .timestamp(LocalTime.now())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(build);
    }
}
