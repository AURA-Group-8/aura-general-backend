package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.message.MessageRequestDto;
import com.aura8.general_backend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagens")
@Tag(name = "Controlador de Mensagens", description = "Controlador de mensagens via WhatsApp")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/all/whatsapp")
    @Operation(summary = "Enviar mensagem para todos os usuários via WhatsApp",
            description = "Envia uma mensagem para todos os usuários cadastrados utilizando o WhatsApp")
    @ApiResponse(responseCode = "200", description = "Mensagens enviadas com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na validação da requisição")
    public ResponseEntity<Void> sendToAllUsersWhatsapp(@Valid @RequestBody MessageRequestDto messageRequestDto){
        messageService.sendToAllUsersWhatsapp(messageRequestDto.getAssunto(), messageRequestDto.getMensagem());
        return ResponseEntity.status(200).build();
    }

}
