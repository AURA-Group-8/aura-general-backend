package com.aura8.general_backend.clean_arch.infrastructure.dto.schedule;

import com.aura8.general_backend.clean_arch.core.domain.enums.PaymentStatus;
import com.aura8.general_backend.clean_arch.core.domain.enums.ScheduleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record SchedulePatchRequest(
        @Schema(description = "Id do usuario", example = "1")
        Integer id,
        @Schema(description = "Feedback do agendamento", example = "1")
        Integer feedback,
        @Schema(description = "Status do Agendamento", example = "PENDENTE")
        ScheduleStatus status,
        @Schema(description = "Status do Pagamento", example = "PENDENTE")
        PaymentStatus paymentStatus
) {
}
