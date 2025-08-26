package org.example.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.dto.LikeRequestDto;
import org.example.dto.LikeResponseDto;
import org.example.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 1. Beğeni ekle (Like)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public LikeResponseDto like(@Valid @RequestBody LikeRequestDto requestDto) {
        return likeService.like(requestDto);
    }

    // 2. Beğeni kaldır (Dislike/Unlike)
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void dislike(@RequestParam @Positive Long userId,
                        @RequestParam @Positive Long tweetId) {
        likeService.dislike(userId, tweetId);
    }
}