package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TmitterErrorResponse {
    private String message;
    private int status;
    private long timestamp;
    private LocalDateTime localDateTime;

    public TmitterErrorResponse(String message, int status, long timestamp, LocalDateTime localDateTime) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.localDateTime = localDateTime;
    }
}
