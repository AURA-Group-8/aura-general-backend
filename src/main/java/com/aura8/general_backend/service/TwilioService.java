package com.aura8.general_backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    public TwilioService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendWhatsappMessage(String phone, String assunto, String mensagem){
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+55%s".formatted(phone)),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        "*%s*\n%s".formatted(assunto, mensagem))
                .create();
    }
}