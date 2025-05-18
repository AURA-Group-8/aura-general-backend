package com.aura8.general_backend.service;

import com.aura8.general_backend.config.MailConfig;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.exception.EmailFailedException;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final UsersService usersService;
    private final TwilioService twilioService;


    private final MailConfig mailConfig;

    public MessageService(UsersService usersService, MailConfig mailConfig) {
        this.usersService = usersService;
        this.mailConfig = mailConfig;
    }

    public void sendToAllUsersWhatsapp(String assunto, String mensagem){
        List<Users> usersList = usersService.getAllUsers();
        if(usersList.isEmpty()) return;
        usersList.forEach(users -> {
            twilioService.sendWhatsappMessage(users.getPhone(), assunto, mensagem);
        });
    }

    @EventListener
    public void onSchedulingCreated(SchedulingCreateEvent event) {
        LocalDateTime localDateTime = event.getScheduling().getStartDatetime();
        String minutoString;
        if (localDateTime.getMinute() < 10) {
            minutoString = "0" + localDateTime.getMinute();
        } else {
            minutoString = "%d".formatted(localDateTime.getMinute());
        }
        String mensagem = "Novo atendimento marcado para %d/%d as %d:%s".formatted(
                localDateTime.getDayOfMonth(),
                localDateTime.getMonthValue(),
                localDateTime.getHour(),
                minutoString
        );
        if (event.getAdminScheduling())
            TwilioService.sendWhatsappMessage(event.getUser().getPhone(), "Novo Atendimento", mensagem);
    }

    public String sendToken(String to) {
        if(!usersService.existsByEmail(to)){
            throw new ElementNotFoundException("Usuario de email: %s não foi encontrado".formatted(to));
        }

        Integer token = (int) Math.ceil(Math.random() * 89999) + 10000;
        String responseToken = token.toString();
        try {
            JavaMailSender emailSender = mailConfig.getJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("aura.noreply@gmail.com");
            message.setTo(to);
            message.setSubject("AURA - Mudar senha");
            message.setText("O codigo para mudar sua senha é: %s".formatted(token));
            emailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
            throw new EmailFailedException("Erro ao enviar e-mail, tente verificar as credênciais");
        }
        return responseToken;
    }
}
