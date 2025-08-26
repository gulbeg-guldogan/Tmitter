package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.TweetPatchRequestDto;
import org.example.dto.TweetRequestDto;
import org.example.dto.TweetResponseDto;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exceptions.TweetNotFoundException;
import org.example.mapper.TweetMapper;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    @Autowired
    private final TweetRepository tweetRepository;

    @Autowired
    private final TweetMapper tweetMapper;

    @Autowired
    private final UserRepository userRepository;

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
    @Transactional
    public TweetResponseDto create(TweetRequestDto dto) {
        // 1) User'ı bul
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.userId()));

        // 2) Tweet'i oluştur ve ilişkiyi set et
        Tweet tweet = new Tweet();
        tweet.setContent(dto.content());
        tweet.setUser(user); // <- Burası kritik!

        // 3) Kaydet ve DTO dön
        Tweet saved = tweetRepository.save(tweet);
        return tweetMapper.toResponseDto(saved);
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
