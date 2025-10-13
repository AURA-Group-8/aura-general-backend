package com.aura8.general_backend.clean_arch.infraestructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.notification.find.FindByUserIdNotificationUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.infraestructure.dto.notification.FindAllByUserIdNotificationResponse;
import com.aura8.general_backend.clean_arch.infraestructure.enums.DirectionEnum;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.NotificationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("NotificationControllerV2")
@RequestMapping("/v2/notificacoes")
public class NotificationController {

    private final FindByUserIdNotificationUseCase findByUserIdNotificationUseCase;

    public NotificationController(FindByUserIdNotificationUseCase findByUserIdNotificationUseCase) {
        this.findByUserIdNotificationUseCase = findByUserIdNotificationUseCase;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{userId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Pegar notificações do usuario", description = "Pega notificações de um usuário")
    public ResponseEntity<Page<FindAllByUserIdNotificationResponse>> getByUserId(
            @PathVariable Integer userId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") DirectionEnum direction
            ){

        Page<Notification> notificationPage = findByUserIdNotificationUseCase.findByUserId(userId, page, size, sortBy, direction.getDirection());
        Page<FindAllByUserIdNotificationResponse> responsePage = notificationPage.map(NotificationMapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }
}
