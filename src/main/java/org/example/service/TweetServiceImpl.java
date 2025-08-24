package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.dto.TweetPatchRequestDto;
import org.example.dto.TweetRequestDto;
import org.example.dto.TweetResponseDto;
import org.example.entity.Tweet;
import org.example.exceptions.TweetNotFoundException;
import org.example.mapper.TweetMapper;
import org.example.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    @Autowired
    @ToString.Exclude
    private final TweetRepository tweetRepository;

    @Autowired
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetResponseDto> getAll() {
        return tweetRepository.findAll().stream().map(u -> tweetMapper.toResponseDto(u)).toList();
    }

    @Override
    public TweetResponseDto get(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if(optionalTweet.isPresent()){
            return tweetMapper.toResponseDto(optionalTweet.get());
        }
        throw new TweetNotFoundException("Tweet bulunamadı id: " + id);
    }

    @Override
    public TweetResponseDto create(TweetRequestDto tweetRequestDto) {
        Tweet tweet = tweetRepository.save(tweetMapper.toEntity(tweetRequestDto));
        return tweetMapper.toResponseDto(tweet);
    }

    @Override
    public TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto) {
        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if(optionalTweet.isPresent()){
            tweet.setTweet_id(id);
            return tweetMapper.toResponseDto(tweetRepository.save(tweet));
        }
        return tweetMapper.toResponseDto(tweetRepository.save(tweet));
    }

    @Override
    public TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto) {
        Tweet tweetToUpdate = tweetRepository
                .findById(id)
                .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı, id: " + id));

        tweetToUpdate = tweetMapper.updateEntity(tweetToUpdate, tweetPatchRequestDto);

        return tweetMapper.toResponseDto(tweetRepository.save(tweetToUpdate));
    }

    @Override
    public void delete(Long id) {
        tweetRepository.deleteById(id);
    }
}
