package com.messageservice.service.impl;

import com.messageservice.dto.UsernameDto;
import com.messageservice.entity.Username;
import com.messageservice.mapper.UsernameMapper;
import com.messageservice.repository.UsernameRepository;
import com.messageservice.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsernameServiceImpl implements UsernameService {

    private final UsernameRepository usernameRepository;
    private final UsernameMapper usernameMapper = UsernameMapper.INSTANCE;

    @Override
    public UsernameDto register(String username) {
        Username usernameEntity = new Username();
        usernameEntity.setUsername(username);
        Username persisted = usernameRepository.save(usernameEntity);
        return usernameMapper.mapToDto(persisted);
    }

    @Override
    @Transactional
    public UsernameDto update(String previousUsername, String updatedUsername) {
        Username persisted = usernameRepository.findByUsername(previousUsername);
        persisted.setUsername(updatedUsername);
        usernameRepository.save(persisted);
        return usernameMapper.mapToDto(persisted);
    }
}
