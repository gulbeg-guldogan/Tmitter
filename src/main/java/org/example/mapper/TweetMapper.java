package org.example.mapper;

import org.example.dto.TweetPatchRequestDto;
import org.example.dto.TweetRequestDto;
import org.example.dto.TweetResponseDto;
import org.example.entity.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public Tweet toEntity(TweetRequestDto tweetRequestDto){
       Tweet tweet = new Tweet();
       tweet.setContent(tweetRequestDto.content());
       return tweet;
    }

    public TweetResponseDto toResponseDto(Tweet tweet){
        return new TweetResponseDto(tweet.getContent());
    }

    public Tweet updateEntity(Tweet tweetToUpdate, TweetPatchRequestDto tweetPatchRequestDto){
        if(tweetPatchRequestDto.content() != null){
            tweetToUpdate.setContent((tweetPatchRequestDto.content()));
        }
        return tweetToUpdate;
    }
}
