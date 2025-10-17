package com.aura8.general_backend.clean_arch.application.usecase.users.create;

import com.aura8.general_backend.clean_arch.application.exception.ElementAlreadyExistsException;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.UsersMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUsersUseCase {

    private final UsersGateway usersGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateUsersUseCase(UsersGateway usersGateway, PasswordEncoder passwordEncoder) {
        this.usersGateway = usersGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Users create(CreateUsersCommand command) {
        if (usersGateway.existsByEmailOrPhone(command.email(), command.phone())){
            throw new ElementAlreadyExistsException("Email ou telefone já cadastrados: %s - %s".formatted(command.email(), command.phone()));
        }
        String encodedPassword = passwordEncoder.encode(command.password());
        CreateUsersCommand commandWithEncodedPassword = new CreateUsersCommand(
                command.username(),
                command.email(),
                encodedPassword,
                command.phone(),
                command.dateOfBirth(),
                command.roleId()
        );
        Users users = UsersMapper.toDomain(commandWithEncodedPassword);
        return usersGateway.save(users);
    }
}
