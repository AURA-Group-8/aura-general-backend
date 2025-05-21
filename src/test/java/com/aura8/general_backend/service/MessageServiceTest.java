package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private UsersService usersService;

    @Mock
    private TwilioService twilioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa envio de mensagem para todos os usuários com telefone válido")
    void testSendToAllUsersWhatsapp_WithUsers() {
        // Arrange
        Users user1 = new Users();
        user1.setPhone("+5511999999999");

        Users user2 = new Users();
        user2.setPhone("+5511888888888");

        when(usersService.getAllUsers()).thenReturn(List.of(user1, user2));

        // Act
        messageService.sendToAllUsersWhatsapp("Assunto", "Mensagem");

        // Assert
        verify(twilioService, times(1)).sendWhatsappMessage("+5511999999999", "Assunto", "Mensagem");
        verify(twilioService, times(1)).sendWhatsappMessage("+5511888888888", "Assunto", "Mensagem");
    }

    @Test
    @DisplayName("Testa envio de mensagem para todos os usuários com lista vazia")
    void testSendToAllUsersWhatsapp_NoUsers() {
        when(usersService.getAllUsers()).thenReturn(List.of());

        messageService.sendToAllUsersWhatsapp("Assunto", "Mensagem");

        verify(twilioService, never()).sendWhatsappMessage(any(), any(), any());
    }

    @Test
    @DisplayName("Testa envio de mensagem para todos os usuários com telefone nulo")
    void testSendToAllUsersWhatsapp_UserWithNullPhone_ShouldNotSendMessage() {
        // Arrange
        Users userWithNullPhone = new Users();
        userWithNullPhone.setPhone(null);

        when(usersService.getAllUsers()).thenReturn(List.of(userWithNullPhone));

        // Act
        messageService.sendToAllUsersWhatsapp("Assunto", "Mensagem");

        // Assert
        verify(twilioService, never()).sendWhatsappMessage(any(), any(), any());
    }
}