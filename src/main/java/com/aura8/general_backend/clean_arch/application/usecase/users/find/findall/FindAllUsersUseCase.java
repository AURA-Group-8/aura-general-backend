package com.aura8.general_backend.clean_arch.application.usecase.users.find.findall;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.users.UsersAdapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllUsersUseCase {
    private final UsersGateway repository;

    public FindAllUsersUseCase(UsersAdapterRepository repository) {
        this.repository = repository;
    }

    public List<Users> findAll() {
        return repository.findAll();
    }
}
