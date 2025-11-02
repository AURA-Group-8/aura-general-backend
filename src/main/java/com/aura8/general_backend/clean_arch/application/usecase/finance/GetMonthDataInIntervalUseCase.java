package com.aura8.general_backend.clean_arch.application.usecase.finance;

import com.aura8.general_backend.clean_arch.core.domain.MonthData;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import com.aura8.general_backend.clean_arch.core.domain.enums.PaymentStatus;
import com.aura8.general_backend.clean_arch.core.domain.enums.ScheduleStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetMonthDataInIntervalUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetMonthDataInIntervalUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public List<MonthData> getMonthDataInInterval(Integer startMonth, Integer endMonth) {
        if(startMonth == -1) {
            List<Schedule> schedulesToGetFirst = scheduleGateway.findAll(0, 1, "startDatetime", "ASC");
            if(schedulesToGetFirst.isEmpty()) {
                startMonth = 1;
            } else {
                startMonth = schedulesToGetFirst.get(0).getStartDatetime().getMonthValue();
            }
        }
        if(endMonth == -1) {
            endMonth = LocalDate.now().getMonthValue();
        }

        List<MonthData> monthDataList = new ArrayList<>();
        for (int month = startMonth; month <= endMonth; month++) {
            LocalDateTime startDate = LocalDate.now().withMonth(month).withDayOfMonth(1).atStartOfDay();
            LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
            List<Schedule> schedules = scheduleGateway.findByStartDateBetween(startDate, endDate, false);
            List<Schedule> canceledSchedules = scheduleGateway.findByStartDateBetween(startDate, endDate, true);
            Double totalBilledInMonth = schedules.stream()
                    .mapToDouble(schedule -> {
                        if(schedule.getPaymentStatus() == PaymentStatus.PAGO) {
                            return schedule.getTotalPrice();
                        } else {
                            return 0.0;
                        }
                    })
                    .sum();
            MonthData monthData = new MonthData(
                    month,
                    totalBilledInMonth,
                    (int) schedules.stream().filter(schedule -> (schedule.getStatus().equals(ScheduleStatus.FEITO))).count(),
                    canceledSchedules.size()
            );
            monthDataList.add(monthData);
        }
        return monthDataList;
    }
}
