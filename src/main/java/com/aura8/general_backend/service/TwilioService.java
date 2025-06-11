package com.aura8.general_backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${twilio.accountsid}")
    public String account_sid;
    @Value("${twilio.auth}")
    public String auth_token;

    @PostConstruct
    public void init() {
        Twilio.init(account_sid, auth_token);
    }

    public static void sendWhatsappMessage(String phone, String assunto, String mensagem){
        try{
            Message message = Message.creator(
                            new PhoneNumber("whatsapp:+55%s".formatted(phone)),
                            new PhoneNumber("whatsapp:+14155238886"),
                            "*%s*\n%s".formatted(assunto, mensagem))
                    .create();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendWhatsappMessage(String phone, String mensagem){
        try{
            Message message = Message.creator(
                            new PhoneNumber("whatsapp:+55%s".formatted(phone)),
                            new PhoneNumber("whatsapp:+14155238886"),
                            "%s".formatted(mensagem))
                    .create();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}