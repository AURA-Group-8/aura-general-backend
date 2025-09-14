package com.aura8.general_backend.core.gateway;

import com.aura8.general_backend.core.domain.Users;

import java.util.List;

public interface UserGateway {
    Users save(Users user);
    List<Users> findAll();
    Users findById(Long id);
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findByPhone(String phone);
    Users update(Users user);
    void delete(Users user);
}
