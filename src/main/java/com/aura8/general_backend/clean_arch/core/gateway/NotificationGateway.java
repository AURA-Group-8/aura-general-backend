package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Notification;

import java.util.List;

public interface NotificationGateway {
    Notification create(Notification notification);
    Notification getById(Integer id);
    Notification patch(Notification notification);
    List<Notification> getAllByUserId(Integer userId, Integer page, Integer size, String sortBy, String direction);
}
