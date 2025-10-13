package com.aura8.general_backend.infraestructure.repository;

import com.aura8.general_backend.infraestructure.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findAllByDeletedFalse();

    Optional<Users> findByEmailAndDeletedFalse(String email);

    Optional<Users> findByIdAndDeletedFalse(Integer id);
}
 