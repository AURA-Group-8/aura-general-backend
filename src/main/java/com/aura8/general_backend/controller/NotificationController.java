package com.aura8.general_backend.controller;

import com.aura8.general_backend.entities.Notification;
import com.aura8.general_backend.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{userId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Pegar notificações do usuario", description = "Pega notificações de um usuário")
    public ResponseEntity<List<Notification>> getByUserId(
            @PathVariable Integer userId
    ){
        List<Notification> notifications = service.getByUserId(userId);
        if(notifications.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(notifications);
    }
}
