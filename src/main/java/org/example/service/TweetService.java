package org.example.service;

import org.example.dto.TweetPatchRequestDto;
import org.example.dto.TweetRequestDto;
import org.example.dto.TweetResponseDto;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAll();
    TweetResponseDto get(Long id);
    TweetResponseDto create(TweetRequestDto tweetRequestDto);
    TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto);
    TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto);
    void delete(Long id);
}
