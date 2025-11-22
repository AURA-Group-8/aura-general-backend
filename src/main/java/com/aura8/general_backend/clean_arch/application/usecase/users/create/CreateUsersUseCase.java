package com.aura8.general_backend.clean_arch.application.usecase.users.create;

import com.aura8.general_backend.clean_arch.application.exception.ElementAlreadyExistsException;
import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Role;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.RoleGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.UsersMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUsersUseCase {

    private final UsersGateway usersGateway;
    private final PasswordEncoder passwordEncoder;
    private final RoleGateway roleGateway;

    public CreateUsersUseCase(UsersGateway usersGateway, PasswordEncoder passwordEncoder, RoleGateway roleGateway) {
        this.usersGateway = usersGateway;
        this.passwordEncoder = passwordEncoder;
        this.roleGateway = roleGateway;
    }

    public Users create(CreateUsersCommand command) {
        if (usersGateway.existsByEmailOrPhone(command.email(), command.phone())){
            throw new ElementAlreadyExistsException("Email ou telefone já cadastrados: %s - %s".formatted(command.email(), command.phone()));
        }

        Role defaultRole = roleGateway.findByName("CLIENTE")
                .orElseThrow(() -> new ElementNotFoundException("Role padrão 'CLIENTE' não encontrada"));

        String encodedPassword = passwordEncoder.encode(command.password());

        Users users = UsersMapper.toDomain(command, encodedPassword, defaultRole);
        return usersGateway.save(users);
    }
}
