package com.messageservice.controller;

import com.messageservice.api.MessageApi;
import com.messageservice.dto.MessageDto;
import com.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController implements MessageApi {
    private final MessageService messageService;
    private final RabbitTemplate amqpTemplate;

    @Override
    public ResponseEntity<Void> sendMessage(MessageDto message) {
        return messageService.sendMessage(message);
    }

    @Override
    public List<MessageDto> showMessages(Long roomId) {
        return messageService.showMessages(roomId);
    }

    @Override
    public ResponseEntity<String> emit(@RequestBody String message){
        log.info("Emit to updateUsernameQueue");
        amqpTemplate.convertAndSend("userExistence", message);
        return ResponseEntity.ok("Success emit to queue");
    }
}
