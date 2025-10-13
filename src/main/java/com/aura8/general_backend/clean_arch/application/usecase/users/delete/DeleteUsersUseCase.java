package com.aura8.general_backend.clean_arch.application.usecase.users.delete;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.application.usecase.users.find.findbyid.FindByIdUsersUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteUsersUseCase {
    private final UsersGateway repository;
    private final FindByIdUsersUseCase findByIdUsersUseCase;

    public DeleteUsersUseCase(UsersGateway repository, FindByIdUsersUseCase findByIdUsersUseCase) {
        this.repository = repository;
        this.findByIdUsersUseCase = findByIdUsersUseCase;
    }

    public void delete(Integer userId) {
        Optional<Users> optionalUsers = findByIdUsersUseCase.findById(userId);
        if (optionalUsers.isEmpty()) {
            throw new ElementNotFoundException("User de id: " + userId + " não encontrado");
        }
        repository.delete(userId);
    }
}
