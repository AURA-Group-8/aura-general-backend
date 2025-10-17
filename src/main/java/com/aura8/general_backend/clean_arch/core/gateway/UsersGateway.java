package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Users;

import java.util.List;
import java.util.Optional;

public interface UsersGateway {
    Users save(Users user);
    List<Users> findAll();
    Optional<Users> findById(Integer id);
    Users findByEmail(String email);
    Users patch(Users user, Integer userId);
    void delete(Integer userId);
    Boolean existsByEmailOrPhone(String email, String phone);
}
