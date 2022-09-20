package com.messageservice.service;

import com.messageservice.dto.UserDto;

import java.util.List;

public interface UsernameService {
    UserDto register(String username);

    UserDto update(String previousUsername, String updatedUsername);

    List<UserDto> getAllUsers();
}
