package com.userservice.service;

public interface RabbitService {
    boolean send(String exchange, String routingKey, Object message);

    boolean send(String routingKey, Object message);
}
