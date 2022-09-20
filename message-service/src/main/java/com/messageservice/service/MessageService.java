package com.messageservice.service;

import com.messageservice.dto.MessageDto;
import com.messageservice.dto.UserDto;
import com.messageservice.service.enums.FormatMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface MessageService {
    ResponseEntity<Void> sendMessage(MessageDto message);

    @GetMapping("/{roomId}")
    List<MessageDto> showMessages(Long roomId);

    List<MessageDto> showMessagesInFormat(FormatMessage formatMessage, Long roomId);
}
