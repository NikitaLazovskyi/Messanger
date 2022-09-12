package com.messageservice.service.impl;

import com.messageservice.dto.MessageDto;
import com.messageservice.entity.Message;
import com.messageservice.mapper.MessageMapper;
import com.messageservice.repository.MessageRepository;
import com.messageservice.repository.UsernameRepository;
import com.messageservice.service.MessageService;
import com.messageservice.service.enums.FormatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UsernameRepository usernameRepository;
    private final MessageMapper mapper = MessageMapper.INSTANCE;

    @Override
    public ResponseEntity<Void> sendMessage(MessageDto messageDto) {
        Message message = mapper.mapToEntity(messageDto);
        message.setUid(UUID.randomUUID());
        message.setTimestamp(Timestamp.from(Instant.now()));
        message.setSender(usernameRepository.findByUsername(messageDto.getSender().getUsername()).orElseThrow(
                () -> new EntityNotFoundException("user with username: " + message.getSender().getUsername() + " doesn't exist")
        ));

        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<MessageDto> showMessages(Long roomId) {
        return messageRepository.findAllByRoomId(roomId, Sort.by("timestamp").descending()).stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> showMessagesInFormat(FormatMessage formatMessage, Long roomId) {
        switch (formatMessage) {
            case TEXT_AUTHOR:
                return messageRepository.findAllByRoomId(roomId, Sort.by("timestamp").descending()).stream().peek(m -> {
                    m.getSender().setId(null);
                    m.setTimestamp(null);
                    m.setUid(null);
                    m.setRoom(null);
                }).map(mapper::mapToDto).collect(Collectors.toList());
            case TEXT_AUTHOR_TIME:
                return messageRepository.findAllByRoomId(roomId, Sort.by("timestamp").descending()).stream().peek(m -> {
                    m.setUid(null);
                    m.setRoom(null);
                }).map(mapper::mapToDto).collect(Collectors.toList());
            default:
                return null;
        }
    }
}
