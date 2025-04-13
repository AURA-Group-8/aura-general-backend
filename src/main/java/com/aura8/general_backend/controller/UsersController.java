package com.aura8.general_backend.controller;

import com.aura8.general_backend.service.UsersService;
import com.aura8.general_backend.entities.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/usuarios")
    public class UsersController {

    @Autowired
    private UsersService service;

    @PostMapping
    public ResponseEntity<Users> register(@Valid @RequestBody Users user) {
        
        service.register(user);
        return ResponseEntity.status(201).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users userInfo) {
        var loginValido = service.login(userInfo);
        if (loginValido) return ResponseEntity.status(200).build();
        throw new RuntimeException();
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {

        List<Users> users = service.getAllUsers();

        if (users.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userToUpdate) {
        Users userUpdate = service.updateUser(id, userToUpdate);
        return ResponseEntity.status(200).body(userUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return ResponseEntity.status(200).build();
    }

}
