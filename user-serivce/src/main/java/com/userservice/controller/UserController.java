package com.userservice.controller;

import com.userservice.api.UserApi;
import com.userservice.dto.UserDto;
import com.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto create(UserDto userDto) {
        return userService.create(userDto);
    }

    @Override
    public ResponseEntity<Void> update(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> login(UserDto userDto) {
        return userService.login(userDto);
    }

    @Override
    public ResponseEntity<Void> logout() {
        return userService.logout();
    }

    @Override
    public UserDto profile() {
        return userService.profile();
    }

    @Override
    public ResponseEntity<Void> delete(String userName) {
        return userService.delete(userName);
    }

    @Override
    public UserDto getByEmail(String email) {
        return userService.getByEmail(email);
    }

    @Override
    public UserDto getByUserName(String userName) {
        return userService.getByUserName(userName);
    }
}
