package com.aura8.general_backend.rabbitmq.json;

import org.springframework.stereotype.Component;

@Component
public class MessageJson {
    private String phone;
    private String subject;
    private String message;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public MessageJson createMessage(String phone, String subject, String message) {
        MessageJson msg = new MessageJson();
        msg.setPhone(phone);
        msg.setSubject(subject);
        msg.setMessage(message);
        return msg;
    }
    public MessageJson createMessage(String phone, String message) {
        MessageJson msg = new MessageJson();
        msg.setPhone(phone);
        msg.setMessage(message);
        return msg;
    }
}