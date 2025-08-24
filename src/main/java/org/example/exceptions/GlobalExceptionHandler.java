package org.example.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<TmitterErrorResponse>  handleException(TmitterException tmitterException){
        TmitterErrorResponse response = new TmitterErrorResponse(
                tmitterException.getMessage(),
                tmitterException.getHttpStatus().value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );
                return new ResponseEntity<>(response, tmitterException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<TmitterErrorResponse>  handleException(Exception exception){
        TmitterErrorResponse response = new TmitterErrorResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
