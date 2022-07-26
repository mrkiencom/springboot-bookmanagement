package com.novahub.javatrain.javaspringbookmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EmailInvalidException extends RuntimeException{
    
    public EmailInvalidException(String email) {
        super(String.format("Email %s is invalid", email));
    }
    
    public EmailInvalidException() {
        super(String.format("Email is invalid"));
    }
}
