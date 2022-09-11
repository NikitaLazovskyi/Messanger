package com.messageservice.service;

import com.messageservice.dto.UsernameDto;

public interface UsernameService {
    UsernameDto register(String username);
    UsernameDto update(String previousUsername, String updatedUsername);
}
