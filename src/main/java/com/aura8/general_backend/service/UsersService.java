package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.exception.ElementAlreadyExists;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.exception.UnauthorizedUserException;
import com.aura8.general_backend.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (!userSenhaCorreto) throw new UnauthorizedUserException("Email ou Senha incorretos");  // 401
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

            Users user = repository.findById(userToUpdate.getId())
                    .orElseThrow(
                            () -> new ElementNotFoundException("notFound")
                    );

            userToUpdate.setRole(roleService.getRoleById(roleId));


            if (userToUpdate.getUsername() == null) userToUpdate.setUsername(user.getUsername());
            if (userToUpdate.getPassword() == null) userToUpdate.setPassword(user.getPassword());
            if (userToUpdate.getEmail() == null) userToUpdate.setEmail(user.getEmail());
            if (userToUpdate.getPhone() == null) userToUpdate.setPhone(user.getPhone());
            if (userToUpdate.getDateOfBirth() == null) userToUpdate.setDateOfBirth(user.getDateOfBirth());
            if (userToUpdate.getCreatedAt() == null) userToUpdate.setCreatedAt(user.getCreatedAt());
            userToUpdate.setModifiedAt(LocalDateTime.now());
            Users updatedUser = repository.save(userToUpdate);
            return updatedUser;


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
