package com.aura8.general_backend.dtos.finance;

import io.swagger.v3.oas.annotations.media.Schema;

public record MonthDataHistoryDto(
        @Schema(description = "Mês do histórico", example = "1")
        Integer mes,
        @Schema(description = "Total faturado no mês", example = "10000.00")
        Double totalFaturadoMes,
        @Schema(description = "Total de atendimentos no mês", example = "10")
        Integer totalAtendimentosMes,
        @Schema(description = "Total de atendimentos cancelados no mês", example = "1")
        Integer totalAtendimentosCanceladosMes
) {
}
