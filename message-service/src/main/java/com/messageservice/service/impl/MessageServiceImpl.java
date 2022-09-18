package com.messageservice.service.impl;

import com.messageservice.dto.MessageDto;
import com.messageservice.entity.Message;
import com.messageservice.mapper.MessageMapper;
import com.messageservice.repository.MessageRepository;
import com.messageservice.repository.UsernameRepository;
import com.messageservice.service.MessageService;
import com.messageservice.service.enums.FormatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UsernameRepository usernameRepository;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Override
    public ResponseEntity<Void> sendMessage(MessageDto messageDto) {
        Message message = messageMapper.mapToEntity(messageDto);
        message.setUid(UUID.randomUUID());
        message.setTimestamp(Timestamp.from(Instant.now()));
        message.setSender(usernameRepository.findByUsername(messageDto.getSender().getUsername()).orElseThrow(
                () -> new EntityNotFoundException("user with username: " + message.getSender().getUsername() + " doesn't exist")
        ));

        messageRepository.save(message);
        log.info("Message was sent with UID: {}", message.getUid());
        return ResponseEntity.ok().build();
    }

    @Override
    public List<MessageDto> showMessages(Long roomId) {
        List<MessageDto> messagesDto = messageRepository.findAllByRoomId(roomId, Sort.by("timestamp").descending())
                .stream().map(messageMapper::mapToDto).collect(Collectors.toList());
        log.info("Messages were requested for room with id: {}", roomId);
        return messagesDto;
    }

    @Override
    public List<MessageDto> showMessagesInFormat(FormatMessage formatMessage, Long roomId) {
        List<MessageDto> messages;
        switch (formatMessage) {
            case TEXT_AUTHOR:
                messages = messageRepository.findAllByRoomId(roomId, Sort.by("timestamp").descending()).stream().peek(m -> {
                    m.getSender().setId(null);
                    m.setTimestamp(null);
                    m.setUid(null);
                    m.setRoom(null);
                }).map(messageMapper::mapToDto).collect(Collectors.toList());
                log.info("Messages were requested in format {} for room with id: {}", formatMessage.toString().toLowerCase(), roomId);
                break;
            case TEXT_AUTHOR_TIME:
                messages = messageRepository.findAllByRoomId(roomId, Sort.by("timestamp").descending()).stream().peek(m -> {
                    m.setUid(null);
                    m.setRoom(null);
                }).map(messageMapper::mapToDto).collect(Collectors.toList());
                log.info("Messages were requested in format {} for room with id: {}", formatMessage.toString().toLowerCase(), roomId);
                break;
            default:
                messages = null;
        }
        return messages;
    }
}
