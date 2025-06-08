package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Notification;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.enums.UpdateTypeEnum;
import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.event.SchedulingDeletedEvent;
import com.aura8.general_backend.event.SchedulingUpdatedEvent;
import com.aura8.general_backend.repository.NotificationRespository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRespository respository;
    private final ApplicationEventPublisher publisher;

    public NotificationService(NotificationRespository respository, ApplicationEventPublisher publisher) {
        this.respository = respository;
        this.publisher = publisher;
    }

    @EventListener
    public void schedulingCreated(SchedulingCreateEvent event){
        Notification notification = new Notification();
        String username = event.getUser().getUsername();
        int adminId = 1;
        Integer receiver = adminId;
        if(event.getAdminScheduling()){
            receiver = event.getUser().getId();
            String adminName = "Kathelyn";
            username = adminName;
        }
        notification.setHasButtonToRate(false);
        notification.setUserId(receiver);
        notification.setMessage("%s agendou um atendimento com você as %d:%d no dia %d/%d/%d".formatted(
                username,
                event.getScheduling().getStartDatetime().getHour(),
                event.getScheduling().getStartDatetime().getMinute(),
                event.getScheduling().getStartDatetime().getDayOfMonth(),
                event.getScheduling().getStartDatetime().getMonthValue(),
                event.getScheduling().getStartDatetime().getYear()
        ));
        notification.setSchedulingId(event.getScheduling().getId());
        respository.save(notification);
    }

    @EventListener
    public void shcedulingDeleted(SchedulingDeletedEvent event){
        Notification notification = new Notification();
        int adminId = 1;
        Integer receiverId = adminId;
        String username = event.getUser().getUsername();
        if(event.getAdmin()){
            username = "Kathelyn";
            receiverId = event.getUser().getId();
        }
        notification.setUserId(receiverId);
        notification.setHasButtonToRate(false);
        notification.setMessage("%s cancelou o atendimento das %d:%d do dia %d/%d/%d; motivo: %s".formatted(
                username,
                event.getScheduling().getStartDatetime().getHour(),
                event.getScheduling().getStartDatetime().getMinute(),
                event.getScheduling().getStartDatetime().getDayOfMonth(),
                event.getScheduling().getStartDatetime().getMonthValue(),
                event.getScheduling().getStartDatetime().getYear(),
                event.getMessage()
        ));
        notification.setSchedulingId(event.getScheduling().getId());
        respository.save(notification);
    }

    @EventListener
    public void schedulingUpdate(SchedulingUpdatedEvent event){
        Notification notification = new Notification();
        notification.setMessage("%s deu nota %d de 5 para o seu primeiro agendamento".formatted(
                event.getUser().getUsername(),
                event.getScheduling().getFeedback()
        ));
        notification.setHasButtonToRate(false);
        notification.setSchedulingId(event.getScheduling().getId());
        int adminId = 1;
        notification.setUserId(adminId);
        if(event.getUpdateType().equals(UpdateTypeEnum.STATUS)){
            notification.setMessage("Soubemos que realizou seu primeiro atendimento! Nos dê o seu feedback, para podermos melhorar sua experiência e mantê-lo conosco!");
            notification.setHasButtonToRate(true);
            notification.setUserId(event.getUser().getId());
            notification.setWasAnswered(false);
        } else {
            Notification notificationToUpdate = respository.findBySchedulingIdAndWasAnsweredFalse(event.getScheduling().getId());
            notificationToUpdate.setWasAnswered(true);
            respository.save(notificationToUpdate);
        }
        respository.save(notification);
    }

    public List<Notification> getByUserId(Integer userId){
        List<Notification> notifications = respository.findByUserId(userId);
        return notifications;
    }
}
