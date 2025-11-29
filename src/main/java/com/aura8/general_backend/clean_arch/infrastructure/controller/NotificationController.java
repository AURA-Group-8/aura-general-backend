package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.notification.find.FindByUserIdNotificationUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.notification.patch.PatchNotificationCommand;
import com.aura8.general_backend.clean_arch.application.usecase.notification.patch.PatchNotificationUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;
import com.aura8.general_backend.clean_arch.infrastructure.dto.notification.FindAllByUserIdNotificationResponse;
import com.aura8.general_backend.clean_arch.infrastructure.dto.notification.PatchNotificationRequest;
import com.aura8.general_backend.clean_arch.infrastructure.dto.notification.PatchNotificationResponse;
import com.aura8.general_backend.clean_arch.infrastructure.enums.DirectionEnum;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.NotificationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificationController {

    private final FindByUserIdNotificationUseCase findByUserIdNotificationUseCase;
    private final PatchNotificationUseCase patchNotificationUseCase;

    public NotificationController(FindByUserIdNotificationUseCase findByUserIdNotificationUseCase,
                                  PatchNotificationUseCase patchNotificationUseCase) {
        this.findByUserIdNotificationUseCase = findByUserIdNotificationUseCase;
        this.patchNotificationUseCase = patchNotificationUseCase;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{userId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Pegar notificações do usuario", description = "Pega notificações de um usuário")
    public ResponseEntity<PageElement<FindAllByUserIdNotificationResponse>> getByUserId(
            @PathVariable Integer userId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") DirectionEnum direction
            ){

        PageElement<Notification> notificationPage = findByUserIdNotificationUseCase.findByUserIdPageable(userId, page, size, sortBy, direction.getDirection());
        List<FindAllByUserIdNotificationResponse> notificationResponses = notificationPage.getContent().stream()
                .map(notification -> NotificationMapper.toResponse((Notification) notification))
                .toList();
        PageElement<FindAllByUserIdNotificationResponse> responsePage = new PageElement<>(
                notificationResponses,
                notificationPage.getPageNumber(),
                notificationPage.getPageSize(),
                notificationPage.getTotalElements(),
                notificationPage.getTotalPages()
        );
        return ResponseEntity.ok(responsePage);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/{notificationId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar notificação", description = "Atualiza uma notificação")
    public ResponseEntity<PatchNotificationResponse> patchNotification(
            @PathVariable Integer notificationId,
            @RequestBody PatchNotificationRequest request
    ) {
        PatchNotificationCommand command = NotificationMapper.toCommand(request);
        Notification patch = patchNotificationUseCase.patch(notificationId, command);
        PatchNotificationResponse response = NotificationMapper.toPatchResponse(patch);
        return ResponseEntity.ok(response);
    }
}
