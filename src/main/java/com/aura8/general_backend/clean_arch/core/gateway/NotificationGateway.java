package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;

import java.util.List;
import java.util.Optional;

public interface NotificationGateway {
    Notification create(Notification notification);
    Optional<Notification> findById(Integer id);
    Notification patch(Notification notification);
    List<Notification> getAllByUserId(Integer userId, Integer page, Integer size, String sortBy, String direction);
    PageElement<Notification> getAllByUserIdPageable(Integer userId, Integer page, Integer size, String sortBy, String direction);
}
