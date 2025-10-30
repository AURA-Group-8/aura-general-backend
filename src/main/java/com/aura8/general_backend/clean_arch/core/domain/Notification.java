package com.aura8.general_backend.clean_arch.core.domain;

import com.aura8.general_backend.clean_arch.core.domain.enums.NotificationType;

public class Notification {
    private Integer id;
    private Users users;
    private Schedule schedule;
    private String message;
    private Boolean hasButtonToRate;
    private Boolean isAnswered = false;
    private Boolean isRead = false;

    public Notification(Users users, Schedule schedule, NotificationType type) {
        this.users = users;
        this.schedule = schedule;
        completeInfosByType(type);
    }

    public Notification(Boolean hasButtonToRate, String message, Schedule schedule, Users users, Integer id) {
        this.hasButtonToRate = hasButtonToRate;
        this.message = message;
        this.schedule = schedule;
        this.users = users;
        this.id = id;
    }

    public Notification(Integer id, Users users, Schedule schedule, String message, Boolean hasButtonToRate, Boolean isAnswered, Boolean isRead) {
        this.id = id;
        this.users = users;
        this.schedule = schedule;
        this.message = message;
        this.hasButtonToRate = hasButtonToRate;
        this.isAnswered = isAnswered;
        this.isRead = isRead;
    }

    public void completeInfosByType(NotificationType type) {
        switch (type) {
            case SCHEDULE_CONFIRMED:
                this.setHasButtonToRate(false);
                this.setMessage("%s agendou um atendimento com você ás %s no dia %02d/%02d/%04d".formatted(
                        type.getActor(),
                        this.schedule.getStartDatetime().toLocalTime().toString(),
                        this.schedule.getStartDatetime().getDayOfMonth(),
                        this.schedule.getStartDatetime().getMonthValue(),
                        this.schedule.getStartDatetime().getYear()
                ));
                break;
            case SCHEDULE_CANCELED:
                this.setHasButtonToRate(false);
                this.setMessage("%s cancelou o atendimento das %s do dia %02d/%02d/%04d. Motivo: %s".formatted(
                        type.getActor(),
                        this.schedule.getStartDatetime().toLocalTime().toString(),
                        this.schedule.getStartDatetime().getDayOfMonth(),
                        this.schedule.getStartDatetime().getMonthValue(),
                        this.schedule.getStartDatetime().getYear(),
                        type.getMessage()
                ));
                break;
            case RATE_SERVICE:
                this.setMessage("%s deu nota %d de 5 para o seu primeiro agendamento".formatted(
                        type.getActor(),
                        this.schedule.getFeedback()
                ));
                this.setHasButtonToRate(false);
                this.isAnswered = true;
                break;
            case FIRST_SCHEDULE_COMPLETED:
                this.setMessage("Soubemos que realizou seu primeiro atendimento! Nos dê o seu feedback, para podermos melhorar sua experiência e mantê-lo conosco!");
                this.setHasButtonToRate(true);
                break;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Schedule getScheduling() {
        return schedule;
    }

    public void setScheduling(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasButtonToRate() {
        return hasButtonToRate;
    }

    public void setHasButtonToRate(Boolean hasButtonToRate) {
        this.hasButtonToRate = hasButtonToRate;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
