package com.aura8.general_backend.clean_arch.application.usecase.notification.find;

import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.notification.NotificationAdapterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FindByUserIdNotificationUseCase {

    private final NotificationAdapterRepository repository;

    public FindByUserIdNotificationUseCase(NotificationAdapterRepository repository) {
        this.repository = repository;
    }

    public Page<Notification> findByUserId(Integer userId, Integer page, Integer size, String sortBy, String direction) {
        List<Notification> notificationList = repository.getAllByUserId(userId, page, size, sortBy, direction);
        Page<Notification> notificationPage = new PageImpl<>(notificationList, Pageable.ofSize(size).withPage(page), notificationList.size());
        return notificationPage;
    }
}
