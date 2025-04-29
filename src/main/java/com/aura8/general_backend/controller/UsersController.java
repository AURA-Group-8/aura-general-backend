package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.*;
import com.aura8.general_backend.service.UsersService;
import com.aura8.general_backend.entities.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/usuarios")
    @Tag(name = "Usuarios", description = "Controlador de usuarios")
    public class UsersController {

    @Autowired
    private UsersService service;

    @CrossOrigin(origins = "*")
    @PostMapping
    @Operation(summary = "Registrar um novo usuario", description = "Registra um novo usuario no sistema")
    @ApiResponse(responseCode = "201", description = "Usuario registrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já existe")
    public ResponseEntity<UsersRegisterResponseDto> register(@Valid @RequestBody UsersRegisterDto user) {
        Users userEntity = UsersMapper.toEntity(user);
        Users savedUser = service.register(userEntity, user.getRoleId());
        UsersRegisterResponseDto userDto = UsersMapper.toResponse(userEntity);
        return ResponseEntity.status(201).body(userDto);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário no sistema")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<UsersTokenDto> login(@Valid @RequestBody UsersLoginDto userInfo) {
        Users userEntity = UsersMapper.toEntity(userInfo);
        UsersTokenDto user = service.login(userEntity);
        return ResponseEntity.status(200).body(user);
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
    public ResponseEntity<List<UsersRegisterResponseDto>> getAllUsers() {

        List<Users> users = service.getAllUsers();

        if (users.isEmpty()) return ResponseEntity.status(204).build();

        List<UsersRegisterResponseDto> usersDto = users.stream().map(UsersMapper::toResponse).toList();
        return ResponseEntity.status(200).body(usersDto);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Obtém os detalhes de um usuário pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UsersRegisterResponseDto> getUserById(@PathVariable Integer id) {
        Users user = service.getUserById(id);
        UsersRegisterResponseDto userDto = UsersMapper.toResponse(user);
        return ResponseEntity.status(200).body(userDto);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UsersUpdateResponseDto> updateUser(@PathVariable Integer id,@Valid @RequestBody UsersUpdateDto userToUpdate) {
        userToUpdate.setId(id);
        Users usersToUpdateEntity = UsersMapper.updateToEntity(userToUpdate);
        Users userUpdate = service.updateUser(userToUpdate.getRoleId(), usersToUpdateEntity);
        UsersUpdateResponseDto userResponse = UsersMapper.updateToResponse(userUpdate);
        return ResponseEntity.status(200).body(userResponse);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Marca um usuário como deletado pelo ID fornecido")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return ResponseEntity.status(204).build();
    }

}
