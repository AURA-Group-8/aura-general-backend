package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.role;

import com.aura8.general_backend.clean_arch.core.domain.Role;
import com.aura8.general_backend.clean_arch.core.gateway.RoleGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.RoleMapper;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleAdapterRepository implements RoleGateway {

    private final RoleJpaRepository repository;

    public RoleAdapterRepository(RoleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findById(Integer id) {
        Optional<RoleEntity> optionalRoleEntity = repository.findById(id);
        return processOptionalRole(optionalRoleEntity);
    }

    @Override
    public Optional<Role> findByName(String name) {
        Optional<RoleEntity> optionalRoleEntity = repository.findByName(name);
        return processOptionalRole(optionalRoleEntity);
    }

    public Optional<Role> processOptionalRole(Optional<RoleEntity> optionalRoleEntity) {
        if (optionalRoleEntity.isEmpty()) {
            return Optional.empty();
        }
        RoleEntity roleEntity = optionalRoleEntity.get();
        Role domain = RoleMapper.toDomain(roleEntity);
        return Optional.of(domain);
    }
}
