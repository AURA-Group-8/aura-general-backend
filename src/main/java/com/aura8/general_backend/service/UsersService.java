package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.exception.ElementAlreadyExists;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService{

    @Autowired
    private UsersRepository repository;

    @Autowired
    private RoleService roleService;

    public Users register(Users user, Integer roleId) {
        user.setRole(roleService.getRoleById(roleId));
        Optional<Users> existingUser = repository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ElementAlreadyExists();
        }

        return repository.save(user);
    }

    public Boolean login(Users userInfo) {
        var userSenhaCorreto = repository.existsByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());
        if (!userSenhaCorreto) throw new RuntimeException(); // 401
        return userSenhaCorreto;

    }

    public List<Users> getAllUsers() {
        return repository.findAllByDeletedFalse();
    }

    public Users getUserById(Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ElementNotFoundException("Usuario de ID: %d não foi encontrado".formatted(id)) // 404
        );

    }

    public Users updateUser(Integer roleId, Users userToUpdate) {
        boolean exists = repository.existsById(userToUpdate.getId());

        if (exists) {
            userToUpdate.setRole(roleService.getRoleById(roleId));
            Users updatedUser = repository.save(userToUpdate);
            return updatedUser;
        }
        throw new ElementNotFoundException("Usuario de ID: %d não foi encontrado".formatted(userToUpdate.getId())); // 404
    }

    public void deleteUser(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException();
        }
        Users userToModify = repository.getById(id);
        userToModify.setDeleted(true);
        repository.save(userToModify);// 404
    }

}
