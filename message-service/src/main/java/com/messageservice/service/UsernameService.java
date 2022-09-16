package com.messageservice.service;

import com.messageservice.dto.UserDto;

public interface UsernameService {
    UserDto register(String username);
    UserDto update(String previousUsername, String updatedUsername);
}
