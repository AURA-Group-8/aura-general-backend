package com.aura8.general_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.aura8.general_backend.entities.Role;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.exception.ElementAlreadyExists;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.exception.UnauthorizedUserException;
import com.aura8.general_backend.repository.UsersRepository;
import com.aura8.general_backend.service.RoleService;
import com.aura8.general_backend.service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
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

        Users result = userService.register(user, 1);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(mockRole, result.getRole());
        verify(repository).save(user);
    }

    @Test
    @DisplayName("Testa o registro de um usuário já existente")
    void testRegisterUserAlreadyExistsThrowsException() {
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
    void testRegisterShouldThrowExceptionWhenEmailIsNull() {
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
    void testRegisterShouldThrowExceptionWhenPasswordIsNull() {
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

    @Test
    @DisplayName("Testa o retorno de um usuário por ID quando existe e não está deletado")
    void shouldReturnUserWhenExistsAndNotDeleted() {
        Users mockUser = new Users();
        mockUser.setId(1);
        mockUser.setUsername("João");
        mockUser.setDeleted(false);

        when(repository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(mockUser));

        Users result = userService.getUserById(1);

        assertEquals("João", result.getUsername());
    }

    @Test
    @DisplayName("Testa quando o usuário não é encontrado durante o login")
    void shouldThrowExceptionWhenUserNotFound() {
        Mockito.when(repository.findByIdAndDeletedFalse(99)).thenReturn(Optional.empty());

        ElementNotFoundException thrown = assertThrows(
                ElementNotFoundException.class,
                () -> userService.getUserById(99)
        );

        assertTrue(thrown.getMessage().contains("Usuario de ID: 99 não foi encontrado"));
    }

    @Test
    @DisplayName("Teste a atualização de um usuário existente com todos os dados preenchidos")
    void shouldFullyUpdateUserWithAllFieldsProvided() {
        Users originalUser = new Users();
        originalUser.setId(1);
        originalUser.setUsername("joao123");
        originalUser.setEmail("joao@email.com");
        originalUser.setPassword("senha123");
        originalUser.setPhone("9999-9999");
        originalUser.setDateOfBirth(LocalDate.of(2000, 1, 1).atStartOfDay());
        originalUser.setDeleted(false);
        originalUser.setCreatedAt(LocalDateTime.now().of(2020, 1, 1, 0, 0));
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        originalUser.setRole(role);

        Users userToUpdate = new Users();
        userToUpdate.setId(1);
        userToUpdate.setUsername("joao_atualizado");
        userToUpdate.setEmail("joao_novo@email.com");
        userToUpdate.setPassword("novaSenha");
        userToUpdate.setPhone("8888-8888");
        userToUpdate.setDateOfBirth(LocalDate.of(1999, 5, 5).atStartOfDay());

        Role novaRole = new Role();
        novaRole.setId(2);
        novaRole.setName("ADMIN");

        when(repository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(originalUser));
        when(roleService.getRoleById(2)).thenReturn(novaRole);
        when(repository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Users atualizado = userService.updateUser(2, userToUpdate);

        assertEquals("joao_atualizado", atualizado.getUsername());
        assertEquals("ADMIN", atualizado.getRole().getName());
    }

    @Test
    @DisplayName("Teste para manter os dados antigos ao atualizar com campos nulos")
    void shouldKeepOldDataIfFieldsAreNull() {
        Users originalUser = new Users();
        originalUser.setId(1);
        originalUser.setUsername("joao123");
        originalUser.setEmail("joao@email.com");
        originalUser.setPassword("senha123");
        originalUser.setPhone("9999-9999");
        originalUser.setDateOfBirth(LocalDate.of(2000, 1, 1).atStartOfDay());
        originalUser.setDeleted(false);
        originalUser.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0));
        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        originalUser.setRole(role);

        Users userToUpdate = new Users();
        userToUpdate.setId(1);

        when(repository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(originalUser));
        when(roleService.getRoleById(1)).thenReturn(originalUser.getRole());
        when(repository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Users atualizado = userService.updateUser(null, userToUpdate);

        assertEquals("joao123", atualizado.getUsername());
        assertEquals("joao@email.com", atualizado.getEmail());
        assertEquals("senha123", atualizado.getPassword());
        assertEquals("USER", atualizado.getRole().getName());
    }

    @Test
    @DisplayName("Teste para marcar usuário como deletado quando ID válido for fornecido")
    void shouldMarkUserAsDeletedWhenValidIdIsProvided() {

        Users user = new Users();
        user.setId(1);
        user.setDeleted(false);

        when(repository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(user));
        when(repository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.deleteUser(1);


        verify(repository, times(1)).findByIdAndDeletedFalse(1);
        verify(repository, times(1)).save(user);

        assertTrue(user.getDeleted());
    }

    @Test
    @DisplayName("Teste para lançar exceção quando tentar deletar usuário com ID inexistente")
    void shouldThrowExceptionWhenIdDoesNotExistWhileDeletingUser() {
        when(repository.findByIdAndDeletedFalse(99)).thenReturn(Optional.empty());

        ElementNotFoundException exception = assertThrows(
                ElementNotFoundException.class,
                () -> userService.deleteUser(99)
        );

        assertTrue(exception.getMessage().contains("Usuario de ID: 99 não foi encontrado"));

        verify(repository, times(1)).findByIdAndDeletedFalse(99);
        verify(repository, never()).save(any());
    }
}
