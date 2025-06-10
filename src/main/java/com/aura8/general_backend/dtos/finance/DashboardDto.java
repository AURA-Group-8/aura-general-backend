package com.aura8.general_backend.dtos.finance;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DashboardDto(
        @Schema(description = "Dados mensais", example = "1")
        MonthDataDto dadosMensais,
        @Schema(description = "Serviços mais realizados", example = "['Sombrancelha', 'Alongamento']")
        List<String> topServicos,
        @Schema(description = "Clientes com mais agendamentos", example = "['Jão', 'Maria']")
        List<String> topClientes,
        @Schema(description = "Quantidade de agendamentos por dia da semana naquele mês, de domingo a sábado", example = "[1, 2, 3, 4, 5, 6, 7]")
        List<Integer> atendimentosDiaDaSemanaNoMes
) {

}
