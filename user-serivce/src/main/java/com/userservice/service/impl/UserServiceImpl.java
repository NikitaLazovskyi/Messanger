package com.userservice.service.impl;

import com.userservice.bean.Session;
import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import com.userservice.exception.InvalidOperationException;
import com.userservice.exception.UnauthorizedAccessException;
import com.userservice.mapper.UserMapper;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Session session;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.mapUser(userDto);
        userRepository.save(user);
        return null;
    }

    @Override
    public ResponseEntity<Void> update(UserDto userDto) {
        User user = userRepository.findByUserName(userDto.getUserName()).orElseThrow(() -> new EntityNotFoundException("User with username: " + userDto.getUserName() + " doesn't exist"));
        userMapper.updateUserFieldsWithDto(userDto, user);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> login(UserDto userDto) {
        if (session.isLogged()) {
            throw new InvalidOperationException("User already logged in");
        }
        User user = userRepository.findByUserName(userDto.getUserName()).orElseThrow(() -> new EntityNotFoundException("User with username: " + userDto.getUserName() + " doesn't exist"));
        if (userDto.getPassword().equals(user.getPassword())) {
            session.setUser(userMapper.mapUserDto(user));
            return ResponseEntity.ok().build();
        } else {
            throw new UnauthorizedAccessException("Wrong password");
        }
    }

    @Override
    public UserDto profile() {
        if (session.isLogged())
            return session.getUser();
        else
            throw new UnauthorizedAccessException("User is not logged in");
    }

    @Override
    public ResponseEntity<Void> logout() {
        if (!session.isLogged()) {
            throw new InvalidOperationException("User is not logged in");
        }
        session.setUser(null);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new EntityNotFoundException("User with username " + userName + " not found"));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        return userMapper.mapUserDto(user);
    }

    @Override
    public UserDto getByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new EntityNotFoundException("User with username " + userName + " not found"));
        return userMapper.mapUserDto(user);
    }
}
