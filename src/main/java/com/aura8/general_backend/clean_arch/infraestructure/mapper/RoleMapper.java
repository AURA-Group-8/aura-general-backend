package com.aura8.general_backend.clean_arch.infraestructure.mapper;

import com.aura8.general_backend.clean_arch.core.domain.Role;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.RoleEntity;

public class RoleMapper {
    public static Role toDomain(RoleEntity entity) {
        if (entity == null) return null;
        return new Role(
                entity.getId(),
                entity.getName()
        );
    }
}
