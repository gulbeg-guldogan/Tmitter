package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class LikeNotFoundException extends TmitterException{
    public LikeNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
