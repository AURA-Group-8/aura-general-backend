package com.aura8.general_backend.clean_arch.application.usecase.finance;

import com.aura8.general_backend.clean_arch.core.domain.*;
import com.aura8.general_backend.clean_arch.core.gateway.JobScheduleGateway;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetDashboardInfosUseCase {

    private final JobScheduleGateway jobScheduleGateway;
    private final ScheduleGateway scheduleGateway;
    private final GetMonthDataInIntervalUseCase getMonthDataInIntervalUseCase;

    public GetDashboardInfosUseCase(JobScheduleGateway jobScheduleGateway,
                                    ScheduleGateway scheduleGateway,
                                    GetMonthDataInIntervalUseCase getMonthDataInIntervalUseCase) {
        this.jobScheduleGateway = jobScheduleGateway;
        this.scheduleGateway = scheduleGateway;
        this.getMonthDataInIntervalUseCase = getMonthDataInIntervalUseCase;
    }

    public DashboardInfosDto getDashboardInfos() {
        Integer currentMonth = LocalDate.now().getMonthValue();
        List<MonthData> monthDataInInterval = getMonthDataInIntervalUseCase.getMonthDataInInterval(currentMonth, currentMonth);
        MonthData monthData = monthDataInInterval.getFirst();
        List<String> topServicos = jobScheduleGateway.findTopServicos();
        List<String> topUsers = jobScheduleGateway.findTopUsers();
        List<Integer> atendimentosDiaDaSemanaNoMes = getAtendimentosDiaDaSemanaNoMes(LocalDate.now());

        DashboardInfosDto dashboardInfosDto = new DashboardInfosDto(
                monthData,
                topServicos,
                topUsers,
                atendimentosDiaDaSemanaNoMes
        );

        return dashboardInfosDto;
    }

    public List<Integer> getAtendimentosDiaDaSemanaNoMes(LocalDate month) {
        LocalDateTime startOfMonth = month.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = month.withDayOfMonth(month.lengthOfMonth()).atTime(23, 59, 59);
        List<Schedule> schedulings = scheduleGateway.findByStartDateBetween(startOfMonth, endOfMonth, false);
        List<Integer> atendimentosDiaDaSemana = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            atendimentosDiaDaSemana.add(0);
        }
        schedulings.forEach(
                scheduling -> {
                    int index_dia_da_semana = scheduling.getStartDatetime().getDayOfWeek().getValue() - 1;
                    int oldValue = atendimentosDiaDaSemana.get(index_dia_da_semana);
                    atendimentosDiaDaSemana.set(index_dia_da_semana, oldValue+1);
                }
        );
        System.out.println("Atendimentos por dia da semana: " + atendimentosDiaDaSemana);
        return atendimentosDiaDaSemana;
    }

}
