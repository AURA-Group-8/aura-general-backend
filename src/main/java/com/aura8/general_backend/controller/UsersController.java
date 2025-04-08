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
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<Users> register(@Valid @RequestBody Users user) {
        return usersService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users userInfo) {
        return usersService.login(userInfo);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        return usersService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userToUpdate) {
        return usersService.updateUser(id, userToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        return usersService.deleteUser(id);
    }

}
