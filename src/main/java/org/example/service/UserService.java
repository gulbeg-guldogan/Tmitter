package org.example.service;

import org.example.dto.UserPathRequestDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAll();
    UserResponseDto get(Long id);
    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto replaceOrCreate(Long id, UserRequestDto userRequestDto);
    UserResponseDto update(Long id, UserPathRequestDto userPathRequestDto);
    void delete(Long id);
}
