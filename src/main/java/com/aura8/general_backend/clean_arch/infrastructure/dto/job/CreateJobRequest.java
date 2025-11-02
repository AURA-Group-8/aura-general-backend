package com.aura8.general_backend.clean_arch.infrastructure.dto.job;

import jakarta.validation.constraints.*;

public record CreateJobRequest(
        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,
        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String description,
        @NotNull(message = "A duração esperada é obrigatória")
        @Positive(message = "A duração deve ser maior que zero")
        Integer expectedDurationMinutes,
        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.1", message = "O preço deve ser maior que 0.1")
        Double price
) {
}
