package com.aura8.general_backend.clean_arch.application.usecase.finance;

import com.aura8.general_backend.clean_arch.core.domain.MonthData;

import java.util.List;

public record DashboardInfosDto(
        MonthData dadosMensais,
        List<String> topServicos,
        List<String> topClientes,
        List<Integer> atendimentosDiaDaSemanaNoMes
) {
}
