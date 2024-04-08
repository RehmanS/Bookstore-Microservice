package com.bookstore.libraryservice.client;

import com.bookstore.libraryservice.exception.ErrorResponse;
import com.bookstore.libraryservice.exception.BookNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

public class RetrieveMessageErrorDecoder implements ErrorDecoder {
    /*  NOTE: This class turns the exception from the feign client into a normal exception
          A common-io dependency should have been added to facilitate stream processes  */
    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse message = null;
        try (InputStream body = response.body().asInputStream()) {
            message = new ErrorResponse((LocalTime) response.headers().get("date").toArray()[0],
                    response.status(),
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    response.request().url());

        } catch (IOException exception) {
            return new Exception(exception.getMessage());
        }
        if (response.status() == 404) {
            throw new BookNotFoundException(message.getMessage());
        }
        return errorDecoder.decode(methodKey, response);
    }
}