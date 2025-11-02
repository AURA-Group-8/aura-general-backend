package com.aura8.general_backend.clean_arch.core.domain;

import java.time.LocalDate;

public class MonthData {
    private Integer month;
    private LocalDate firstDayOfMonth;
    private Double totalBilledInMonth;
    private Integer totalSchedulesInMonth;
    private Integer totalCanceledSchedulesInMonth;

    public MonthData(LocalDate dayOfMonth, Double totalBilledInMonth, Integer totalSchedulesInMonth, Integer totalCanceledSchedulesInMonth) {
        this.month = dayOfMonth.getMonthValue();
        this.firstDayOfMonth = dayOfMonth.withDayOfMonth(1);
        this.totalBilledInMonth = totalBilledInMonth;
        this.totalSchedulesInMonth = totalSchedulesInMonth;
        this.totalCanceledSchedulesInMonth = totalCanceledSchedulesInMonth;
    }

    public MonthData(Integer monthNumber, Double totalBilledInMonth, Integer totalSchedulesInMonth, Integer totalCanceledSchedulesInMonth) {
        this.month = monthNumber;
        this.firstDayOfMonth = LocalDate.now().withMonth(monthNumber).withDayOfMonth(1);
        this.totalBilledInMonth = totalBilledInMonth;
        this.totalSchedulesInMonth = totalSchedulesInMonth;
        this.totalCanceledSchedulesInMonth = totalCanceledSchedulesInMonth;
    }

    public void setTotalBilledInMonth(Double totalBilledInMonth) {
        this.totalBilledInMonth = totalBilledInMonth;
    }

    public void setTotalSchedulesInMonth(Integer totalSchedulesInMonth) {
        this.totalSchedulesInMonth = totalSchedulesInMonth;
    }

    public void setTotalCanceledSchedulesInMonth(Integer totalCanceledSchedulesInMonth) {
        this.totalCanceledSchedulesInMonth = totalCanceledSchedulesInMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public LocalDate getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public Double getTotalBilledInMonth() {
        return totalBilledInMonth;
    }

    public Integer getTotalSchedulesInMonth() {
        return totalSchedulesInMonth;
    }

    public Integer getTotalCanceledSchedulesInMonth() {
        return totalCanceledSchedulesInMonth;
    }
}
