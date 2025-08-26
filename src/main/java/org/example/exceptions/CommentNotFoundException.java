package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends  TmitterException{
    public CommentNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
