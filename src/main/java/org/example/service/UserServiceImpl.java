package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.dto.UserPathRequestDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.example.exceptions.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    @ToString.Exclude
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    /*@Autowired
    public TmitterServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }*/

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                //.map(userMapper::toResponseDto)
                .map(u -> userMapper.toResponseDto(u))
                .toList();
    }

    @Override
    public UserResponseDto get(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return userMapper.toResponseDto(optionalUser.get());
        }

        throw new UserNotFoundException("User bulunamadı id: " + id);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {

        User user = userRepository.save(userMapper.toEntity(userRequestDto));
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto replaceOrCreate(Long id, UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            //Update
            user.setUserId(id);
            return userMapper.toResponseDto(userRepository.save(user));
        }
        //INSERT INTO
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto update(Long id, UserPathRequestDto userPathRequestDto) {
        User userToUpdate = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı, id: " + id));

        userToUpdate = userMapper.updateEntity(userToUpdate, userPathRequestDto);

        return userMapper.toResponseDto(userRepository.save(userToUpdate));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
