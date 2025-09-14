package com.aura8.general_backend.core.domain.user;

import com.aura8.general_backend.core.domain.Role;
import com.aura8.general_backend.core.domain.Users;
import com.aura8.general_backend.core.domain.attribute.Email;
import com.aura8.general_backend.core.domain.attribute.Password;
import com.aura8.general_backend.core.domain.attribute.Phone;
import com.aura8.general_backend.core.domain.attribute.Username;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsersTest {

    @Test
    @DisplayName("Deve criar um usuário com todos os atributos válidos")
    void testCreateUser() {
        Username username = new Username("validUser");
        Email email = new Email("valid@email.com");
        Password password = new Password("ValidPass123!");
        Phone phone = new Phone("12345678901");
        LocalDateTime dateOfBirth = LocalDateTime.of(1990, 1, 1, 0, 0);
        String observation = "This is a valid observation.";
        Role role = new Role("USER");
        Users user = new Users(
                username,
                email,
                password,
                phone,
                dateOfBirth,
                observation,
                role
        );
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    @DisplayName("Não deve criar um usuário com telefone inválido")
    void testCreateUserWithInvalidPhone() {
        try {
            Username username = new Username("validUser");
            Email email = new Email("valid@email.com");
            Password password = new Password("ValidPass123!");
            Phone phone = new Phone("(12)34567-8901");
            LocalDateTime dateOfBirth = LocalDateTime.of(1990, 1, 1, 0, 0);
            String observation = "This is a valid observation.";
            Role role = new Role("USER");
            Users user = new Users(
                    username,
                    email,
                    password,
                    phone,
                    dateOfBirth,
                    observation,
                    role
            );
            System.out.println(user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "Phone (12)34567-8901 não é válido");
        }
    }
}