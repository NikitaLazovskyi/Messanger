package com.messageservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SwaggerConfig.class, RabbitConfiguration.class, ValidatorConfig.class})
public class Config {
}

