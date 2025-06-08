package com.aura8.general_backend.repository;

import com.aura8.general_backend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRespository extends JpaRepository<Notification, Integer> {

    Notification findBySchedulingIdAndWasAnsweredFalse(Integer schedulingId);

    List<Notification> findByUserId(Integer userId);
}
