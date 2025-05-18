package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.event.SchedulingCreateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final UsersService usersService;
    private final TwilioService twilioService;

    public MessageService(UsersService usersService, TwilioService twilioService) {
        this.usersService = usersService;
        this.twilioService = twilioService;
    }

    public void sendToAllUsersWhatsapp(String assunto, String mensagem){
        List<Users> usersList = usersService.getAllUsers();
        if(usersList.isEmpty()) return;
        usersList.forEach(users -> {
            twilioService.sendWhatsappMessage(users.getPhone(), assunto, mensagem);
        });
    }

    @EventListener
    public void onSchedulingCreated(SchedulingCreateEvent event){
        LocalDateTime localDateTime = event.getScheduling().getStartDatetime();
        String minutoString;
        if(localDateTime.getMinute() < 10) {
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
        if(event.getAdminScheduling()) TwilioService.sendWhatsappMessage(event.getUser().getPhone(), "Novo Atendimento", mensagem);

    }
}
