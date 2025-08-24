package org.example.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class TweetNotFoundException extends TmitterException {
    public TweetNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
