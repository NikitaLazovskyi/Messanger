package com.messageservice.service.impl;

import com.messageservice.dto.UserDto;
import com.messageservice.entity.User;
import com.messageservice.mapper.UsernameMapper;
import com.messageservice.repository.UsernameRepository;
import com.messageservice.service.UsernameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsernameServiceImpl implements UsernameService {

    private final UsernameRepository usernameRepository;
    private final UsernameMapper usernameMapper = UsernameMapper.INSTANCE;

    @Override
    public UserDto register(String username) {
        User userEntity = new User();
        userEntity.setUsername(username);
        User persisted = usernameRepository.save(userEntity);
        log.info("The username {} was registered", username);
        return usernameMapper.mapToDto(persisted);
    }

    @Override
    @Transactional
    public UserDto update(String previousUsername, String updatedUsername) {
        User persisted = usernameRepository.findByUsername(previousUsername).orElseThrow(
                ()->new EntityNotFoundException("user with username: " + previousUsername + " doesn't exist")
        );
        persisted.setUsername(updatedUsername);
        usernameRepository.save(persisted);
        log.info("The username {} was updated to {}", previousUsername, updatedUsername);
        return usernameMapper.mapToDto(persisted);
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> allUsers = usernameRepository.findAll().stream().map(usernameMapper::mapToDto).collect(Collectors.toList());
        log.info("All users list was requested");
        return allUsers;
    }
}
