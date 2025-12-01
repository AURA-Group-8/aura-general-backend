package com.aura8.general_backend.clean_arch.application.usecase.users.login;

import com.aura8.general_backend.clean_arch.application.exception.ForbiddenException;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.UsersToken;
import com.aura8.general_backend.clean_arch.core.gateway.SecurityGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginUsersUseCase {
    private final UsersGateway usersGateway;
    private final SecurityGateway securityGateway;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redis;

    public LoginUsersUseCase(UsersGateway usersGateway,
                             SecurityGateway securityGateway,
                             PasswordEncoder passwordEncoder,
                             StringRedisTemplate redis) {
        this.usersGateway = usersGateway;
        this.securityGateway = securityGateway;
        this.passwordEncoder = passwordEncoder;
        this.redis = redis;
    }

    public UsersToken login(LoginUsersCommand command) {
        String key = "login:" + command.email();
        ValueOperations<String, String> ops = redis.opsForValue();
        String attemptsStr = ops.get(key);
        int qtdTentativas = attemptsStr == null ? 0 : Integer.parseInt(attemptsStr);

        if (qtdTentativas >= 5) {
            throw new ForbiddenException("Usuário Bloqueado tente novamente mais tarde!");
        }

        Boolean userExists = usersGateway.existsByEmail(command.email());
        if (!userExists) {
            throw new ForbiddenException("Invalid email or password");
        }

        UsersToken token;
        try {
            token = securityGateway.getTokenFromLogin(command.email(), command.password());
        } catch (Exception e) {
            ops.set(key, String.valueOf(qtdTentativas + 1), 60, TimeUnit.SECONDS);
            throw new ForbiddenException("Senha incorreta!");
        }

        try {
            redis.delete(key);
        } catch (Exception ignored) {
        }

        Users users = usersGateway.findByEmail(command.email())
                .orElseThrow(() -> new ForbiddenException("Usuário não encontrado"));

        token.setId(users.getId());
        token.setUsername(users.getUsername().get());
        token.setEmail(users.getEmail().get());
        return token;
    }
}
