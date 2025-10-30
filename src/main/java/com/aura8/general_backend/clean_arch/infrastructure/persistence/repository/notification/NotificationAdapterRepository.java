package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.notification;

import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.core.gateway.NotificationGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.NotificationMapper;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationAdapterRepository implements NotificationGateway {

    private final NotificationJpaRepository repository;

    public NotificationAdapterRepository(NotificationJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification create(Notification notification) {
        NotificationEntity entity = NotificationMapper.toEntity(notification);
        NotificationEntity savedEntity = repository.save(entity);
        return NotificationMapper.toDomain(savedEntity);
    }

    @Override
    public Notification getById(Integer id) {
        return null;
    }

    @Override
    public Notification patch(Notification notification) {
        return null;
    }

    @Override
    public List<Notification> getAllByUserId(Integer userId, Integer page, Integer size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NotificationEntity> notificationPage = repository.findAllByUserId(pageable, userId);
        List<Notification> notificationList = notificationPage.getContent().stream().map(NotificationMapper::toDomain).toList();
        return notificationList;
    }
}
