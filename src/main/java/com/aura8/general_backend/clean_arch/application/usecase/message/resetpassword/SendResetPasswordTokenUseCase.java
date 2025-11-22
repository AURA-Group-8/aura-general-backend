package com.aura8.general_backend.clean_arch.application.usecase.message.resetpassword;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import com.aura8.general_backend.clean_arch.core.gateway.MessageGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SendResetPasswordTokenUseCase {

    private final MessageGateway messageGateway;
    private final UsersGateway usersGateway;

    public SendResetPasswordTokenUseCase(MessageGateway messageGateway, UsersGateway usersGateway) {
        this.messageGateway = messageGateway;
        this.usersGateway = usersGateway;
    }

    public ChangePasswordInfos sendToken(Email toEmail) {

        Optional<Users> usersOptional = usersGateway.findByEmail(toEmail.get());
        if (usersOptional.isEmpty()) {
            throw new ElementNotFoundException("Usuário não encontrado para o email: " + toEmail.get());
        }

        Integer token = (int) Math.ceil(Math.random() * 89999) + 10000;
        String responseToken = token.toString();
        Email fromEmail = new Email("aura.noreply@gmail.com");
        String subject = "AURA - Mudar senha";
        String text = "O codigo para mudar sua senha é: %s".formatted(token);
        messageGateway.sendMessageEmail(fromEmail, toEmail, subject, text);

        return new ChangePasswordInfos(responseToken, usersOptional.get().getId());
    }
}
