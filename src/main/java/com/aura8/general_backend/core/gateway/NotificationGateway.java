package com.aura8.general_backend.core.gateway;

import com.aura8.general_backend.core.domain.Notification;

import java.util.List;

public interface NotificationGateway {
    Notification create(Notification notification);
    Notification getById(Integer id);
    void delete(Integer id);
    Notification update(Notification notification);
    List<Notification> getAllByUserId(Integer userId);
}
