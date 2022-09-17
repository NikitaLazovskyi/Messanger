package com.messageservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messageservice.dto.UsernameRegisterMessageDto;
import com.messageservice.dto.UsernameUpdateMessageDto;
import com.messageservice.repository.UsernameRepository;
import com.messageservice.service.UsernameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUsernameListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UsernameService usernameService;

    @RabbitListener(queues = "userRegisterQueue")
    public void processMessageRegister(String message){
        log.info("Received from queue {}", message);
        UsernameRegisterMessageDto obj;
        try {
             obj = objectMapper.readValue(message, UsernameRegisterMessageDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        usernameService.register(obj.getUsername());
    }

    @RabbitListener(queues = "userUpdateQueue")
    public void processMessageUpdate(String message){
        log.info("Received from queue {}", message);
        UsernameUpdateMessageDto obj;
        try {
            obj = objectMapper.readValue(message, UsernameUpdateMessageDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        usernameService.update(obj.getPreviousName(), obj.getUpdatedName());
    }
}
