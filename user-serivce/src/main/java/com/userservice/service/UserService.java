package com.userservice.service;

import com.userservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);
    ResponseEntity<Void> update(UserDto userDto);
    ResponseEntity<Void> login(UserDto userDto);
    ResponseEntity<Void> logout();
    public ResponseEntity<Void> delete(String username);
    UserDto getByEmail(String email);
    UserDto getByUserName(String userName);
}
