package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Users;
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

    public ResponseEntity<Users> register(Users user) {
        Optional<Users> existingUser = repository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Users savedUser = repository.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    public ResponseEntity<Users> login(Users userInfo) {
        Optional<Users> user = repository.findByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());
        return ResponseEntity.of(user);
    }

    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> allUsers = repository.findAllByDeletedFalse();
        if (allUsers.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allUsers);
    }

    public ResponseEntity<Users> getUserById(Integer id) {
        Optional<Users> user = repository.findById(id);
        return user.map(value -> ResponseEntity.status(200).body(value)) // OK
                .orElseGet(() -> ResponseEntity.status(404).build());  // Not Found
    }
    // Update user
    public ResponseEntity<Users> updateUser(Integer id, Users userToUpdate) {
        boolean exists = repository.existsById(id);

        if (exists) {
            userToUpdate.setId(id);
            Users updatedUser = repository.save(userToUpdate);
            return ResponseEntity.status(200).body(updatedUser); // OK
        }
        return ResponseEntity.status(404).build(); // Not Found
    }

    public ResponseEntity<Void> deleteUser(Integer id) {
        if (repository.existsById(id)) {
            Users userToModify = repository.getById(id);
            userToModify.setDeleted(true);
            repository.save(userToModify);
            return ResponseEntity.status(204).build(); // No Content
        }
        return ResponseEntity.status(404).build(); // Not Found
    }

}
