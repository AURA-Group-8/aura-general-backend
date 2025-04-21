package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.message.MessageRequestDto;
import com.aura8.general_backend.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagens")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/all/whatsapp")
    public ResponseEntity<Void> sendToAllUsersWhatsapp(@Valid @RequestBody MessageRequestDto messageRequestDto){
        messageService.sendToAllUsersWhatsapp(messageRequestDto.getAssunto(), messageRequestDto.getMensagem());
        return ResponseEntity.status(200).build();
    }

}
