package org.example.dto;

public record LikeRequestDto(
        Long userId,
        Long tweetId
) {
}