package com.aura8.general_backend.clean_arch.infraestructure.dto.job;

import jakarta.validation.constraints.*;

public record PatchJobRequest(
        Integer id,
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String description,
        @Positive(message = "A duração deve ser maior que zero")
        Integer expectedDurationMinutes,
        @DecimalMin(value = "0.1", message = "O preço deve ser maior que 0.1")
        Double price
) {
}
