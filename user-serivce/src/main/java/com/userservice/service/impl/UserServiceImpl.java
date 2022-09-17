package com.userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.bean.Session;
import com.userservice.dto.UserDto;
import com.userservice.dto.UsernameRegisterMessageDto;
import com.userservice.dto.UsernameUpdateMessageDto;
import com.userservice.entity.User;
import com.userservice.exception.InvalidOperationException;
import com.userservice.exception.UnauthorizedAccessException;
import com.userservice.mapper.UserMapper;
import com.userservice.repository.UserRepository;
import com.userservice.repository.impl.enums.SelectUser;
import com.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Session session;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userMapper.mapUser(userDto);
        HashMap<SelectUser, Boolean> constraints = new HashMap<>();
        constraints.put(SelectUser.EMAIL, null);
        constraints.put(SelectUser.USERNAME, null);
        constraints.entrySet().stream().peek(e -> e.setValue(userRepository.existsBy(e.getKey(), userDto.getUserName())))
                .forEach(entry -> {
            if (entry.getValue()) {
                throw new EntityExistsException(String.format(
                        "This %s already exist",
                        entry.getKey().toString().toLowerCase()));
            }
        });
        User persisted = userRepository.insert(user);
        log.info("User was created with username: {} and email: {}", persisted.getUserName(), persisted.getEmail());
        String body;
        try {
            body = objectMapper.writeValueAsString(new UsernameRegisterMessageDto(userDto.getUserName()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        rabbitTemplate.convertAndSend("userRegisterQueue", body);
        return userMapper.mapUserDto(persisted);
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        SelectUser updateBy;
        User user = userRepository.findBy(userDto.getEmail(), SelectUser.EMAIL).orElse(null);
        if (user != null) {
            updateBy = SelectUser.EMAIL;
        } else {
            user = userRepository.findBy(userDto.getUserName(), SelectUser.USERNAME).orElseThrow(() -> new EntityNotFoundException("User with username: " + userDto.getUserName() + " doesn't exist"));
            if (user != null) {
                updateBy = SelectUser.USERNAME;
            } else {
                throw new EntityNotFoundException(String.format("User with username: %s and/or email: %s doesn't exist", user.getUserName(), user.getEmail()));
            }
        }
        userMapper.updateUserFieldsWithDto(userDto, user);
        User persisted = userRepository.update(user, updateBy);
        log.info("User was updated with username: {} and email: {}", persisted.getUserName(), persisted.getEmail());

        //we are interested only in username changes
        if (!userDto.getUserName().equals(persisted.getUserName())) {
            String body;
            try {
                body = objectMapper.writeValueAsString(new UsernameUpdateMessageDto(persisted.getUserName(), userDto.getUserName()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            rabbitTemplate.convertAndSend("userUpdateQueue", body);
        }
        return userMapper.mapUserDto(persisted);
    }

    @Override
    public ResponseEntity<Void> login(UserDto userDto) {
        if (session.isLogged()) {
            throw new InvalidOperationException("User already logged in");
        }
        User user = userRepository.findBy(userDto.getUserName(), SelectUser.USERNAME).orElseThrow(
                () -> new EntityNotFoundException("User with username: " + userDto.getUserName() + " doesn't exist")
        );
        if (userDto.getPassword().equals(user.getPassword())) {
            session.setUser(userMapper.mapUserDto(user));
        } else {
            throw new UnauthorizedAccessException("Wrong password");
        }

        log.info("User was logged in with username: {} and email: {}", session.getUser().getUserName(), session.getUser().getEmail());
        return ResponseEntity.ok().build();
    }

    @Override
    public UserDto profile() {
        if (session.isLogged()) return session.getUser();
        else throw new UnauthorizedAccessException("User is not logged in");
    }

    @Override
    public ResponseEntity<Void> logout() {
        if (!session.isLogged()) {
            throw new InvalidOperationException("User is not logged in");
        }
        String email = session.getUser().getEmail();
        String username = session.getUser().getUserName();
        session.setUser(null);
        log.info("User was logged out with username: {} and email: {}", username, email);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(String userName) {
        User user = userRepository.findBy(userName, SelectUser.USERNAME).orElseThrow(() -> new EntityNotFoundException("User with username " + userName + " not found"));
        userRepository.delete(user);
        log.info("User was deleted with username: {} and email: {}", user.getUserName(), user.getEmail());
        return ResponseEntity.ok().build();
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findBy(email, SelectUser.EMAIL).orElseThrow(() -> new EntityNotFoundException("User with email " + email + " was not found"));
        log.info("User was requested by email: {}", user.getEmail());
        return userMapper.mapUserDto(user);
    }

    @Override
    public UserDto getByUserName(String userName) {
        User user = userRepository.findBy(userName, SelectUser.USERNAME).orElseThrow(() -> new EntityNotFoundException("User with username " + userName + " was not found"));
        log.info("User was requested by username: {}", user.getUserName());
        return userMapper.mapUserDto(user);
    }

    @Override
    public List<UserDto> allUsers() {
        log.info("All users were requested");
        return userRepository.findAll().stream().map(userMapper::mapUserDto).collect(Collectors.toList());
    }
}
