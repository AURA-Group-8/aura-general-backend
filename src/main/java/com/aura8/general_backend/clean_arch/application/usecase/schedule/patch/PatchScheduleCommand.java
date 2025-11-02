package com.aura8.general_backend.clean_arch.application.usecase.schedule.patch;

import com.aura8.general_backend.clean_arch.core.domain.enums.PaymentStatus;
import com.aura8.general_backend.clean_arch.core.domain.enums.ScheduleStatus;

public record PatchScheduleCommand(
        Integer id,
        Integer feedback,
        ScheduleStatus status,
        PaymentStatus paymentStatus
) {
}
