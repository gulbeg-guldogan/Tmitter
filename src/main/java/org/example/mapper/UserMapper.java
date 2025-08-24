package org.example.mapper;

import org.example.dto.UserPathRequestDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDto userRequestDto){
        User user = new User();
        user.setUsername(userRequestDto.username());
        user.setEmail(userRequestDto.email());
        user.setPassword(userRequestDto.password());
        return user;
    }

    public UserResponseDto toResponseDto(User user){
        return new UserResponseDto(user.getUsername(), user.getEmail(), user.getPassword());
    }

    public User updateEntity(User userToUpdate, UserPathRequestDto userPathRequestDto){
        if(userPathRequestDto.username() != null){
            userToUpdate.setUsername(userPathRequestDto.username());
        }

        if(userPathRequestDto.email() != null){
            userToUpdate.setEmail(userPathRequestDto.email());
        }

        if(userPathRequestDto.password() != null){
            userToUpdate.setPassword(userPathRequestDto.password());
        }

        return userToUpdate;
    }
}
