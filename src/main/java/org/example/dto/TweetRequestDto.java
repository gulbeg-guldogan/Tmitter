package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TweetRequestDto(
        @NotEmpty
        @NotBlank
        @NotNull
        @Size(max=400)
        String content,
        @NotNull
        Long userId
) {
}
