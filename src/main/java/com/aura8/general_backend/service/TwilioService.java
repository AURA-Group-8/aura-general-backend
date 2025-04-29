package com.aura8.general_backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${twilio.accountsid}")
    public static final String ACCOUNT_SID = "";
    @Value("${twilio.auth}")
    public static final String AUTH_TOKEN = "";

    public TwilioService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendWhatsappMessage(String phone, String assunto, String mensagem){
        try{
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:+55%s".formatted(phone)),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            "*%s*\n%s".formatted(assunto, mensagem))
                    .create();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}