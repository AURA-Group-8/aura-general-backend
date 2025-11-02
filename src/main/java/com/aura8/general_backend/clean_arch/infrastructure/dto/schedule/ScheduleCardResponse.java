package com.aura8.general_backend.clean_arch.infrastructure.dto.schedule;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleCardResponse (
    Integer idScheduling,
    String userName,
    List<String> jobsNames,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    Double totalPrice,
    String paymentStatus,
    String status
) {
}
