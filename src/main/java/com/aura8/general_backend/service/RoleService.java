package com.aura8.general_backend.service;

import com.aura8.general_backend.infraestructure.entities.Role;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.infraestructure.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public Role getRoleById(Integer id){
        Optional<Role> optionalRole = repository.findById(id);
        if(optionalRole.isEmpty()) throw new ElementNotFoundException("Role de ID: %d não foi encontrada".formatted(id));
        return optionalRole.get();
    }
}
