package com.messageservice.service.impl;

import com.messageservice.dto.MessageDto;
import com.messageservice.mapper.MessageMapper;
import com.messageservice.repository.MessageRepository;
import com.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper mapper = MessageMapper.INSTANCE;

    @Override
    public ResponseEntity<Void> sendMessage(MessageDto message) {
        messageRepository.save(mapper.mapToEntity(message));
        return ResponseEntity.ok().build();
    }

    @Override
    public List<MessageDto> showMessages(Long roomId) {
        return messageRepository.findAllByRoomId(roomId).stream().map(mapper::mapToDto).collect(Collectors.toList());
    }
}
