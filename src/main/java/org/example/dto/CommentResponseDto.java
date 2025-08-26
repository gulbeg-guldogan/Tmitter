package org.example.dto;

public record CommentResponseDto(
        Long id,
        String content,
        UserResponseDto user,
        TweetResponseDto tweet
) {
}