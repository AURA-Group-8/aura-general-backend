package com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.notification;

import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Integer> {

}
