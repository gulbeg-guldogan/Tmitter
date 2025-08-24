package org.example.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserPathRequestDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@Positive @PathVariable("id") Long id){
        return userService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//HTTP 201
    public UserResponseDto create(@Validated @RequestBody UserRequestDto userRequestDto){
        return userService.create(userRequestDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto replaceOrCreate(@Positive @PathVariable("id") Long id,
                                           @Validated @RequestBody UserRequestDto userRequestDto){
        return userService.replaceOrCreate(id, userRequestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto update(@Positive @PathVariable("id") Long id,
                                  @Validated @RequestBody UserPathRequestDto userPathRequestDto){
        return userService.update(id, userPathRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //HTTP 204
    public void delete(@Positive @PathVariable("id") Long id){
        userService.delete(id);
    }
}
