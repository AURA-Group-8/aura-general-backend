package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.users;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersJpaRepository extends JpaRepository<UsersEntity, Integer> {
    List<UsersEntity> findAllByDeletedFalse();

    Page<UsersEntity> findAllByDeletedFalse(Pageable pageable);

    Optional<UsersEntity> findByEmailAndDeletedFalse(String email);

    Optional<UsersEntity> findByIdAndDeletedFalse(Integer id);

    Boolean existsByEmailAndDeletedFalseOrPhoneAndDeletedFalse(String email, String phone);

    Boolean existsByEmailAndDeletedFalse(String email);
}
