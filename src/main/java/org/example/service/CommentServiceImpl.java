package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.entity.Comment;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exceptions.CommentNotFoundException;
import org.example.exceptions.TweetNotFoundException;
import org.example.exceptions.UnauthorizedActionException;
import org.example.exceptions.UserNotFoundException;
import org.example.repository.CommentRepository;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public CommentResponseDto create(CommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + requestDto.userId()));
        Tweet tweet = tweetRepository.findById(requestDto.tweetId())
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found with id: " + requestDto.tweetId()));

        Comment comment = new Comment();
        comment.setContent(requestDto.content());
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        return mapToResponseDto(saved);
    }

    @Override
    public CommentResponseDto getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        return mapToResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getByTweetId(Long tweetId) {
        List<Comment> comments = commentRepository.findAll()
                .stream()
                .filter(c -> c.getTweet().getId().equals(tweetId))
                .collect(Collectors.toList());

        return comments.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto update(Long id, CommentUpdateDto updateDto, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException("You can only update your own comment!");
        }

        comment.setContent(updateDto.content());
        Comment updated = commentRepository.save(comment);

        return mapToResponseDto(updated);
    }

    @Override
    public void delete(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException("You can only delete your own comment!");
        }

        commentRepository.delete(comment);
    }

    private CommentResponseDto mapToResponseDto(Comment comment) {
        UserResponseDto userDto = new UserResponseDto(
                comment.getUser().getUsername(),
                comment.getUser().getEmail(),
                comment.getUser().getPassword()
        );

        TweetResponseDto tweetDto = new TweetResponseDto(
                comment.getTweet().getContent()
        );

        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                userDto,
                tweetDto
        );
    }
}