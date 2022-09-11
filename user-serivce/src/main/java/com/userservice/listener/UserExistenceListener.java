package com.userservice.listener;

import com.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Slf4j
@Component
@RequiredArgsConstructor
public class UserExistenceListener {

    private final UserRepository userRepository;

    @RabbitListener(queues = "userExistence")
    public void processMessage(String message){
        boolean b = userRepository.existsByUserName(message);
        log.info("Existence of user {} : is {}", message, b);
    }
}
