package com.aura8.general_backend.clean_arch.application.usecase.users.find.findbyid;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindByIdUsersUseCase {
    private final UsersGateway repository;

    public FindByIdUsersUseCase(UsersGateway repository) {
        this.repository = repository;
    }

    public Optional<Users> findById(Integer id) {
        return repository.findById(id);
    }
}
