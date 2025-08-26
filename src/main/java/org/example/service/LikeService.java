package org.example.service;

import org.example.dto.LikeRequestDto;
import org.example.dto.LikeResponseDto;

public interface LikeService {
    LikeResponseDto like(LikeRequestDto requestDto);
    void dislike(Long userId, Long tweetId);
}
