package com.aura8.general_backend.clean_arch.infraestructure.controller;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.application.usecase.users.delete.DeleteUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.find.findall.FindAllUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.find.findbyid.FindByIdUsersUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.infraestructure.dto.users.FindUsersResponse;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.UsersMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("UsersControllerV2")
@RequestMapping("/v2/usuarios")
public class UsersController {

    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindByIdUsersUseCase findByIdUsersUseCase;
    private final DeleteUsersUseCase deleteUsersUseCase;

    public UsersController(FindAllUsersUseCase findAllUsersUseCase, FindByIdUsersUseCase findByIdUsersUseCase, DeleteUsersUseCase deleteUsersUseCase) {
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.findByIdUsersUseCase = findByIdUsersUseCase;
        this.deleteUsersUseCase = deleteUsersUseCase;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
    public ResponseEntity<List<FindUsersResponse>> getAllUsers() {

        List<Users> users = findAllUsersUseCase.findAll();

        if (users.isEmpty()) return ResponseEntity.status(204).build();

        List<FindUsersResponse> usersDto = users.stream().map(UsersMapper::toResponse).toList();
        return ResponseEntity.status(200).body(usersDto);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar usuário por ID", description = "Obtém os detalhes de um usuário pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<FindUsersResponse> getUserById(@PathVariable Integer id) {
        Optional<Users> user = findByIdUsersUseCase.findById(id);
        if (user.isEmpty()) throw new ElementNotFoundException("User de id: " + id + " não encontrado");
        FindUsersResponse response = UsersMapper.toResponse(user.get());
        return ResponseEntity.status(200).body(response);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar usuário", description = "Marca um usuário como deletado pelo ID fornecido")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        deleteUsersUseCase.delete(id);
        return ResponseEntity.status(204).build();
    }
}
