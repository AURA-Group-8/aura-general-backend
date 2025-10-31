package com.aura8.general_backend.infraestructure.config;
import org.springframework.amqp.core.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    public static final String SEND_MESSAGE_QUEUE = "SendMessageQueue";
    public static final String SEND_EMAIL_QUEUE = "SendEmailQueue";

    @Bean
    public Queue message() {
        return new Queue(SEND_MESSAGE_QUEUE, false);
    }

    @Bean
    public Queue email() {
    return new Queue(SEND_EMAIL_QUEUE, false);
    }

}
