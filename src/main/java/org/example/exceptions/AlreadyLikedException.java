package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyLikedException extends TmitterException{
    public AlreadyLikedException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
