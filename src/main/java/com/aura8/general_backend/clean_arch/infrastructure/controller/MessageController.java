package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.message.SendMessageToAllWhatsappUsersUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.message.resetpassword.ChangePasswordInfos;
import com.aura8.general_backend.clean_arch.application.usecase.message.resetpassword.SendResetPasswordTokenUseCase;
import com.aura8.general_backend.clean_arch.infrastructure.dto.message.ChangePasswordRequest;
import com.aura8.general_backend.clean_arch.infrastructure.dto.message.ChangePasswordResponse;
import com.aura8.general_backend.clean_arch.infrastructure.dto.message.SendMessageAllUsersRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mensagens")
public class MessageController {

    private final SendMessageToAllWhatsappUsersUseCase sendMessageToAllWhatsappUsersUseCase;
    private final SendResetPasswordTokenUseCase sendResetPasswordTokenUseCase;

    public MessageController(SendMessageToAllWhatsappUsersUseCase sendMessageToAllWhatsappUsersUseCase,
                             SendResetPasswordTokenUseCase sendResetPasswordTokenUseCase) {
        this.sendMessageToAllWhatsappUsersUseCase = sendMessageToAllWhatsappUsersUseCase;
        this.sendResetPasswordTokenUseCase = sendResetPasswordTokenUseCase;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/all/whatsapp")
    @Operation(summary = "Enviar mensagem para todos os usuários via WhatsApp",
            description = "Envia uma mensagem para todos os usuários cadastrados utilizando o WhatsApp")
    @ApiResponse(responseCode = "200", description = "Mensagens enviadas com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na validação da requisição")
    public ResponseEntity<Void> sendToAllUsersWhatsapp(@Valid @RequestBody SendMessageAllUsersRequest messageRequestDto){
        sendMessageToAllWhatsappUsersUseCase.sendToAllUsersWhatsapp(messageRequestDto.assunto(), messageRequestDto.mensagem());
        return ResponseEntity.status(200).build();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/esqueci-senha")
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest requestDto){
        ChangePasswordInfos response = sendResetPasswordTokenUseCase.sendToken(requestDto.email());
        ChangePasswordResponse responseDto = new ChangePasswordResponse(response.token(), response.userId());
        return ResponseEntity.status(200).body(responseDto);
    }
}
