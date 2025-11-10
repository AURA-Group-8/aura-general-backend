package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.notification;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Integer> {
    @Query("SELECT n FROM NotificationEntity n WHERE n.users.id = :userId")
    Page<NotificationEntity> findAllByUserId(Pageable pageable, @Param("userId") Integer userId);
}
