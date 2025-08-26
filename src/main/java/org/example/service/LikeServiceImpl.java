package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LikeRequestDto;
import org.example.dto.LikeResponseDto;
import org.example.dto.TweetResponseDto;
import org.example.dto.UserResponseDto;
import org.example.entity.Like;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exceptions.AlreadyLikedException;
import org.example.exceptions.LikeNotFoundException;
import org.example.exceptions.TweetNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.example.repository.LikeRepository;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;
import org.example.service.LikeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public LikeResponseDto like(LikeRequestDto requestDto) {
        User user = userRepository.findById(requestDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + requestDto.userId()));
        Tweet tweet = tweetRepository.findById(requestDto.tweetId())
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found with id: " + requestDto.tweetId()));

        if (likeRepository.findByUserIdAndTweetId(user.getUserId(), tweet.getTweetId()).isPresent()) {
            throw new AlreadyLikedException("User already liked this tweet");
        }

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        Like saved = likeRepository.save(like);

        return mapToResponseDto(saved);
    }

    @Override
    public void dislike(Long userId, Long tweetId) {
        Like like = likeRepository.findByUserIdAndTweetId(userId, tweetId)
                .orElseThrow(() -> new LikeNotFoundException("Like not found"));
        likeRepository.delete(like);
    }

    private LikeResponseDto mapToResponseDto(Like like) {
        User user = like.getUser();
        Tweet tweet = like.getTweet();

        UserResponseDto userDto = new UserResponseDto(user.getUsername(), user.getEmail(), user.getPassword());
        TweetResponseDto tweetDto = new TweetResponseDto(tweet.getContent());

        return new LikeResponseDto(like.getId(), userDto, tweetDto);
    }
}