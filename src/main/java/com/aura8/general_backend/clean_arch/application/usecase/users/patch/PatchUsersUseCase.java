package com.aura8.general_backend.clean_arch.application.usecase.users.patch;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Role;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.RoleGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.UsersMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatchUsersUseCase {
    private final UsersGateway usersGateway;
    private final RoleGateway roleGateway;
    private final PasswordEncoder passwordEncoder;

    public PatchUsersUseCase(UsersGateway usersGateway, PasswordEncoder passwordEncoder, RoleGateway roleGateway) {
        this.usersGateway = usersGateway;
        this.passwordEncoder = passwordEncoder;
        this.roleGateway = roleGateway;
    }

    public Users patch(PatchUsersCommand command, Integer userId) {
        Optional<Users> optionalUsers = usersGateway.findById(userId);
        if (optionalUsers.isEmpty()) {
            throw new ElementNotFoundException("User de id: " + userId + " não encontrado");
        }

        Optional<Role> optionalRole = roleGateway.findById(command.roleId());
        if (optionalRole.isEmpty()) {
            throw new ElementNotFoundException("Role de id: " + command.roleId() + " não encontrado");
        }

        Users user = optionalUsers.get();
        if (command.password() != null && !command.password().isBlank()) {
            command = new PatchUsersCommand(
                    userId,
                    command.username(),
                    command.email(),
                    passwordEncoder.encode(command.password()),
                    command.phone(),
                    command.dateOfBirth(),
                    command.observation(),
                    command.roleId(),
                    optionalRole.get()
            );
        }
        UsersMapper.mergeToDomain(user, command);
        Users patch = usersGateway.patch(user, userId);
        return patch;
    }
}
