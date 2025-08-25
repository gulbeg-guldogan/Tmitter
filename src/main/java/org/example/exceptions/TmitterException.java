package org.example.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TmitterException extends RuntimeException {
    private HttpStatus httpStatus;
    public TmitterException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
