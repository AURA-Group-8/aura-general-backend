package com.aura8.general_backend.service;

import com.aura8.general_backend.dtos.finance.DashboardDto;
import com.aura8.general_backend.dtos.finance.MonthDataDto;
import com.aura8.general_backend.dtos.finance.MonthDataHistoryDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceService {

    private final SchedulingService schedulingService;
    private final JobSchedulingService jobSchedulingService;

    public FinanceService(SchedulingService schedulingService, JobSchedulingService jobSchedulingService) {
        this.schedulingService = schedulingService;
        this.jobSchedulingService = jobSchedulingService;
    }

    public DashboardDto generateFinanceDashboard() {
        MonthDataDto monthDataDto = schedulingService.generateFinanceMonthData(LocalDate.now());
        List<String> topServicos = jobSchedulingService.getTopServicos();
        List<String> topClientes = schedulingService.getTopClientes();
        List<Integer> atendimentosDiaDaSemanaNoMes = schedulingService.getAtendimentosDiaDaSemanaNoMes(LocalDate.now());
        return new DashboardDto(
                monthDataDto,
                topServicos,
                topClientes,
                atendimentosDiaDaSemanaNoMes
        );
    }

    public List<MonthDataHistoryDto> getHistoricoFinanceiro() {
        LocalDate dateFirstScheduling = schedulingService.getDateFirstScheduling();
        List<MonthDataHistoryDto> monthDataDtos = new ArrayList<>();
        Integer qtdMeses = LocalDate.now().getMonthValue() - dateFirstScheduling.getMonthValue();
        for (int i = 0; i <= qtdMeses; i++) {
            LocalDate monthDate = dateFirstScheduling.plusMonths(i);
            MonthDataDto monthDataDto = schedulingService.generateFinanceMonthData(monthDate);
            MonthDataHistoryDto monthDataHistoryDto = new MonthDataHistoryDto(
                    monthDate.getMonthValue(),
                    monthDataDto.totalFaturadoMes(),
                    monthDataDto.totalAtendimentosMes(),
                    monthDataDto.totalAtendimentosCanceladosMes()
            );
            monthDataDtos.add(monthDataHistoryDto);
        }
        return monthDataDtos;
    }
}
