package com.aura8.general_backend.clean_arch.application.usecase.users.login;

import com.aura8.general_backend.clean_arch.application.exception.ForbiddenException;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.UsersToken;
import com.aura8.general_backend.clean_arch.core.gateway.SecurityGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUsersUseCase {
    private final UsersGateway usersGateway;
    private final SecurityGateway securityGateway;
    private final PasswordEncoder passwordEncoder;

    public LoginUsersUseCase(UsersGateway usersGateway, SecurityGateway securityGateway, PasswordEncoder passwordEncoder) {
        this.usersGateway = usersGateway;
        this.securityGateway = securityGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersToken login(LoginUsersCommand command) {
        String encodedPassword = passwordEncoder.encode(command.password());
        Boolean userExists = usersGateway.existsByEmail(command.email());
        if (!userExists) {
            throw new ForbiddenException("Invalid email or password");
        }
        UsersToken token = securityGateway.getTokenFromLogin(command.email(), command.password());
        Users users = usersGateway.findByEmail(command.email()).get();
        token.setId(users.getId());
        token.setUsername(users.getUsername().get());
        token.setEmail(users.getEmail().get());
        return token;
    }
}
