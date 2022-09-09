package com.messageservice.controller;

import com.messageservice.api.MessageApi;
import com.messageservice.dto.MessageDto;
import com.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService messageService;

    @Override
    public ResponseEntity<Void> sendMessage(MessageDto message) {
        return messageService.sendMessage(message);
    }

    @Override
    public List<MessageDto> showMessages(Long roomId) {
        return messageService.showMessages(roomId);
    }
}
