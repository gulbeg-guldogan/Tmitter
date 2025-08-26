package org.example.dto;

public record LikeResponseDto(
        Long id,
        UserResponseDto user,
        TweetResponseDto tweet
) {
}