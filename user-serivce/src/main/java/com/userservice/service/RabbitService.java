package com.userservice.service;

import org.springframework.amqp.core.Message;

public interface RabbitService {
    boolean send(String exchange, String routingKey, Object message);
    boolean send(String routingKey, Object message);
}
