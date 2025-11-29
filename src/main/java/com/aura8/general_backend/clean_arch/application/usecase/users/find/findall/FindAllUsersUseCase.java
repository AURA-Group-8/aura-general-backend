package com.aura8.general_backend.clean_arch.application.usecase.users.find.findall;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.users.UsersAdapterRepository;
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

    public PageElement<Users> findAllPageable(Integer page, Integer size, String sortBy, String direction) {
        return repository.findAllPageable(page, size, sortBy, direction);
    }
}
