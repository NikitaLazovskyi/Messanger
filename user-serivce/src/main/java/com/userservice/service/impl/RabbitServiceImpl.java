package com.userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.UsernameRegisterMessageDto;
import com.userservice.dto.UsernameUpdateMessageDto;
import com.userservice.exception.InvalidOperationException;
import com.userservice.service.RabbitService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitServiceImpl implements RabbitService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Class<?>> supportedTypes = new ArrayList<>();

    @PostConstruct
    public void init() {
        supportedTypes.add(UsernameRegisterMessageDto.class);
        supportedTypes.add(UsernameUpdateMessageDto.class);
    }

    @Override
    public boolean send(String exchange, String routingKey, Object object) {
        String body;
        if (supportedTypes.contains(object.getClass())) {
            try {
                body = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            rabbitTemplate.convertAndSend(exchange, routingKey, body);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean send(String routingKey, Object object) {
        String body;
        if (supportedTypes.contains(object.getClass())) {
            try {
                body = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            rabbitTemplate.convertAndSend(routingKey, body);
            return true;
        } else {
            return false;
        }
    }
}
