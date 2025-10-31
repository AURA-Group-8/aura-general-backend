package com.aura8.general_backend.dtos.message;

import jakarta.validation.constraints.NotBlank;

public class MessageSendEmailDto {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
