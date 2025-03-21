package com.aura8.general_backend;

import com.aura8.general_backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findAllByDeletedFalse();

    Optional<Users> findByEmailAndPassword(String email, String password);

    Optional<Users> findByEmail(String email);
}
