package com.novahub.javatrain.javaspringbookmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserExistedExeption extends RuntimeException {
    public UserExistedExeption(String email) {
        super(String.format("User is existed with email"+email));
    }
}
