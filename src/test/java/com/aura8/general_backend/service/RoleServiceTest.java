package com.aura8.general_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.infraestructure.entities.Role;
import com.aura8.general_backend.infraestructure.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;


import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository repository;

    @Test
    @DisplayName("Teste para retornar a role quando ID for válido")
    void shouldReturnRoleWhenIdIsValid() {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        when(repository.findById(1)).thenReturn(Optional.of(role));

        Role result = roleService.getRoleById(1);

        assertEquals("ADMIN", result.getName());
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Testar a exceção de elemento não encontrado quando ID da role não for encontrado")
    void shouldThrowExceptionWhenRoleNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        ElementNotFoundException exception = assertThrows(
                ElementNotFoundException.class,
                () -> roleService.getRoleById(99)
        );

        assertTrue(exception.getMessage().contains("Role de ID: 99 não foi encontrada"));
        verify(repository, times(1)).findById(99);
    }
}