package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.*;
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
    public ResponseEntity<UsersRegisterResponseDto> register(@Valid @RequestBody UsersRegisterDto user) {
        Users userEntity = UsersMapper.toEntity(user);
        service.register(userEntity);
        UsersRegisterResponseDto userDto = UsersMapper.toResponse(userEntity);
        return ResponseEntity.status(201).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody UsersLoginDto userInfo) {
        Users userEntity = UsersMapper.toEntity(userInfo);
        service.login(userEntity);
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<List<UsersRegisterResponseDto>> getAllUsers() {

        List<Users> users = service.getAllUsers();

        if (users.isEmpty()) return ResponseEntity.status(204).build();

        List<UsersRegisterResponseDto> usersDto = users.stream().map(UsersMapper::toResponse).toList();
        return ResponseEntity.status(200).body(usersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersRegisterResponseDto> getUserById(@PathVariable Integer id) {
        Users user = service.getUserById(id);
        UsersRegisterResponseDto userDto = UsersMapper.toResponse(user);
        return ResponseEntity.status(200).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersUpdateResponseDto> updateUser(@PathVariable Integer id, @RequestBody UsersUpdateDto userToUpdate) {
        Users usersToUpdateEntity = UsersMapper.updateToEntity(userToUpdate);
        Users userUpdate = service.updateUser(id, usersToUpdateEntity);
        UsersUpdateResponseDto userResponse = UsersMapper.updateToResponse(userUpdate);
        return ResponseEntity.status(200).body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return ResponseEntity.status(200).build();
    }

}
