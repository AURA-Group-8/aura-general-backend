package com.aura8.general_backend.service;

import com.aura8.general_backend.infraestructure.entities.Users;
import com.aura8.general_backend.infraestructure.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {


    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UsersRepository usersRepository;


    @Test
    @DisplayName("Teste para retornar UserDetails quando usuário com e-mail válido for encontrado")
    void shouldReturnUserDetailsWhenEmailIsValid() {
        Users mockUser = new Users();
        mockUser.setEmail("joao@email.com");
        mockUser.setPassword("senha123");
        mockUser.setDeleted(false);

        when(usersRepository.findByEmailAndDeletedFalse("joao@email.com"))
                .thenReturn(Optional.of(mockUser));

        UserDetails result = authenticationService.loadUserByUsername("joao@email.com");

        assertEquals("joao@email.com", result.getUsername());
        assertEquals("senha123", result.getPassword());
    }

    @Test
    @DisplayName("Teste para lançar UsernameNotFoundException quando e-mail não for encontrado")
    void shouldThrowExceptionWhenEmailIsNotFound() {
        when(usersRepository.findByEmailAndDeletedFalse("naoexiste@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername("naoexiste@email.com");
        });

        verify(usersRepository, times(1)).findByEmailAndDeletedFalse("naoexiste@email.com");
    }
}