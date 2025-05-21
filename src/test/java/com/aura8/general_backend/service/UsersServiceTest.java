package com.aura8.general_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.aura8.general_backend.dtos.UsersMapper;
import com.aura8.general_backend.dtos.UsersTokenDto;
import com.aura8.general_backend.entities.Role;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.exception.ElementAlreadyExists;
import com.aura8.general_backend.exception.UnauthorizedUserException;
import com.aura8.general_backend.repository.UsersRepository;
import com.aura8.general_backend.service.RoleService;
import com.aura8.general_backend.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsersServiceTest {

    @InjectMocks
    private UsersService userService;

    @Mock
    private UsersRepository repository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa o registro de um novo usuário com sucesso")
    void testRegister_Success() {
        // Dados simulados
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("rawPassword");

        Role mockRole = new Role();
        when(roleService.getRoleById(1)).thenReturn(mockRole);
        when(repository.findByEmailAndDeletedFalse("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        Users savedUser = new Users();
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(mockRole);
        when(repository.save(any(Users.class))).thenReturn(savedUser);

        // Execução
        Users result = userService.register(user, 1);

        // Verificações
        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(mockRole, result.getRole());
        verify(repository).save(user);
    }

    @Test
    @DisplayName("Testa o registro de um usuário já existente")
    void testRegister_UserAlreadyExists_ThrowsException() {
        Users user = new Users();
        user.setEmail("existing@example.com");

        when(repository.findByEmailAndDeletedFalse("existing@example.com"))
                .thenReturn(Optional.of(new Users()));

        assertThrows(ElementAlreadyExists.class, () -> {
            userService.register(user, 1);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Testa o registro de um usuário com email nulo")
    void testRegister_ShouldThrowException_WhenEmailIsNull() {
        Users user = new Users();
        user.setEmail(null);
        user.setPassword("senha123");

        assertThrows(NullPointerException.class, () -> {
            userService.register(user, 1);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Testa o registro de um usuário com senha nula")
    void testRegister_ShouldThrowException_WhenPasswordIsNull() {
        Users user = new Users();
        user.setPassword(null);
        user.setEmail("test@example.com");

        when(repository.findByEmailAndDeletedFalse("test@example.com")).thenReturn(Optional.empty());
        when(roleService.getRoleById(anyInt())).thenReturn(new Role());

        assertThrows(NullPointerException.class, () -> {
            userService.register(user, 1);
        });

        verify(repository, never()).save(any());
    }

}
