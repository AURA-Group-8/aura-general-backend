package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Role;

import java.util.Optional;

public interface RoleGateway {
    Optional<Role> findById(Integer id);
    Optional<Role> findByName(String name);
}
