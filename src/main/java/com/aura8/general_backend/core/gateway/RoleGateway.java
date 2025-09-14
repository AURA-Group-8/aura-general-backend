package com.aura8.general_backend.core.gateway;

import com.aura8.general_backend.core.domain.Role;

public interface RoleGateway {
    Role findById(Integer id);
}
