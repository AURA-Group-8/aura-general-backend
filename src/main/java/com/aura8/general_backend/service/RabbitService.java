package com.aura8.general_backend.service;

import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.rabbitmq.json.MessageJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageJson messageJson;

    @Autowired
    private ObjectMapper objectMapper;

    // Nome da exchange e routing key
    private static final String QUEUE_NAME = "SendMessageQueue";
    private static final String EMAIL_QUEUE_NAME = "SendEmailQueue";

    public void sendMessage(String phone, String subject, String message) {
        Object payload = messageJson.createMessage(phone, subject, message);
        sendJson(payload, QUEUE_NAME);
        System.out.println("Mensagem enviada");
    }
    public void sendMessage(String phone, String message) {
        Object payload = messageJson.createMessage(phone, message);
        sendJson(payload, QUEUE_NAME);
        System.out.println("Mensagem enviada");
    }

    public void sendCreatedSchedule(SchedulingCreateEvent event) {}

    public void sendEmailToken(SimpleMailMessage mensagem) {
        Object payload = mensagem;
        sendJson(payload, EMAIL_QUEUE_NAME);
        System.out.println("Mensagem enviada");
    }
    public void sendEmailAura(String mensagem) {
        Object payload = mensagem;
        sendJson(payload, EMAIL_QUEUE_NAME);
        System.out.println("Mensagem enviada");
    }




    private void sendJson(Object payload, String queueName) {
        try {
            byte[] json = objectMapper.writeValueAsBytes(payload);
            MessageProperties props = new MessageProperties();
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            Message amqpMessage = new Message(json, props);
            amqpTemplate.send(queueName, amqpMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload to JSON", e);
        }
    }
}