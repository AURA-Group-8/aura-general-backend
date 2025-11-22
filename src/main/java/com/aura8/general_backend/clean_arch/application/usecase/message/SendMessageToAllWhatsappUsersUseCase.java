package com.aura8.general_backend.clean_arch.application.usecase.message;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.MessageGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMessageToAllWhatsappUsersUseCase {

    private final UsersGateway usersGateway;
    private final MessageGateway messageGateway;

    public SendMessageToAllWhatsappUsersUseCase(UsersGateway usersGateway, MessageGateway messageGateway) {
        this.usersGateway = usersGateway;
        this.messageGateway = messageGateway;
    }

    public void sendToAllUsersWhatsapp(String assunto, String mensagem) {
        List<Users> usersList = usersGateway.findAll();
        if (usersList.isEmpty()) return;
        usersList.stream()
                .filter(user -> user.getRole().getName().equals("CLIENTE"))
                .forEach(users -> {
                    messageGateway.sendMessageWhatsapp(users.getPhone(), assunto, mensagem);
                });
    }

}
