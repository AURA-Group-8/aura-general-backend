package com.aura8.general_backend.clean_arch.infrastructure.dto.schedule;

import java.time.LocalDateTime;

public record ScheduleResponse(
    Integer id,
    Integer userId,
    Double totalPrice,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String status,
    String paymentStatus,
    Integer feedback
) {}

