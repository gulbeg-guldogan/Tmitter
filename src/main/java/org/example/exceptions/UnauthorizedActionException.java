package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedActionException extends TmitterException{
    public UnauthorizedActionException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
