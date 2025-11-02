package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.application.usecase.users.create.CreateUsersCommand;
import com.aura8.general_backend.clean_arch.application.usecase.users.create.CreateUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.delete.DeleteUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.find.findall.FindAllUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.find.findbyid.FindByIdUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.login.LoginUsersCommand;
import com.aura8.general_backend.clean_arch.application.usecase.users.login.LoginUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.users.patch.PatchUsersCommand;
import com.aura8.general_backend.clean_arch.application.usecase.users.patch.PatchUsersUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.UsersToken;
import com.aura8.general_backend.clean_arch.infrastructure.dto.users.*;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.UsersMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("UsersControllerV2")
@RequestMapping("/v2/usuarios")
public class UsersController {

    private final CreateUsersUseCase createUsersUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindByIdUsersUseCase findByIdUsersUseCase;
    private final PatchUsersUseCase patchUsersUseCase;
    private final DeleteUsersUseCase deleteUsersUseCase;
    private final LoginUsersUseCase loginUsersUseCase;

    public UsersController(CreateUsersUseCase createUsersUseCase,
                           FindAllUsersUseCase findAllUsersUseCase,
                           FindByIdUsersUseCase findByIdUsersUseCase,
                           PatchUsersUseCase patchUsersUseCase,
                           DeleteUsersUseCase deleteUsersUseCase,
                           LoginUsersUseCase loginUsersUseCase) {
        this.createUsersUseCase = createUsersUseCase;
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.findByIdUsersUseCase = findByIdUsersUseCase;
        this.patchUsersUseCase = patchUsersUseCase;
        this.deleteUsersUseCase = deleteUsersUseCase;
        this.loginUsersUseCase = loginUsersUseCase;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    @Operation(summary = "Registrar um novo usuario", description = "Registra um novo usuario no sistema")
    @ApiResponse(responseCode = "201", description = "Usuario registrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já existe")
    public ResponseEntity<CreateUsersResponse> register(@Valid @RequestBody CreateUsersRequest user) {
        CreateUsersCommand createUsersCommand = UsersMapper.toCommand(user);
        Users users = createUsersUseCase.create(createUsersCommand);
        CreateUsersResponse createResponse = UsersMapper.toCreateResponse(users);
        return ResponseEntity.status(201).body(createResponse);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário no sistema")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<UsersToken> login(@Valid @RequestBody LoginUsersRequest userInfo) {
        LoginUsersCommand loginCommand = UsersMapper.toLoginCommand(userInfo);
        UsersToken token = loginUsersUseCase.login(loginCommand);
        return ResponseEntity.status(200).body(token);
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
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<PatchUsersResponse> updateUser(@PathVariable Integer id, @Valid @RequestBody PatchUsersRequest usersToUpdate) {
        PatchUsersCommand command = UsersMapper.toCommand(usersToUpdate);
        Users patch = patchUsersUseCase.patch(command, id);
        PatchUsersResponse response = UsersMapper.toPatchResponse(patch);
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
