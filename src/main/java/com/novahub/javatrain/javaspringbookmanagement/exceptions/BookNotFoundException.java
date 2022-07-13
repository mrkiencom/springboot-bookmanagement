package com.novahub.javatrain.javaspringbookmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long bookId) {
        super(String.format("Book with id %s could not be found", bookId));
    }
}
