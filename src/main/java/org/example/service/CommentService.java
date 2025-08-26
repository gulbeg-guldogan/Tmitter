package org.example.service;

import org.example.dto.CommentRequestDto;
import org.example.dto.CommentResponseDto;
import org.example.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto create(CommentRequestDto requestDto);
    CommentResponseDto getById(Long id);
    List<CommentResponseDto> getByTweetId(Long tweetId);
    CommentResponseDto update(Long id, CommentUpdateDto updateDto, Long userId);
    void delete(Long id, Long userId);
}