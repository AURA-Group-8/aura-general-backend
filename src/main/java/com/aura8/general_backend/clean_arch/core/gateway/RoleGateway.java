package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Role;

public interface RoleGateway {
    Role findById(Integer id);
}
