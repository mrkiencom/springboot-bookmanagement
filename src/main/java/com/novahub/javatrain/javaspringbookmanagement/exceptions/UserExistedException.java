package com.novahub.javatrain.javaspringbookmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserExistedException extends RuntimeException {
    public UserExistedException(String email) {
        super(String.format("User is existed with email" +email));
    }
}
