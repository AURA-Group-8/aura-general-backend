package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.finance.DashboardInfosDto;
import com.aura8.general_backend.clean_arch.application.usecase.finance.GetDashboardInfosUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.finance.GetMonthDataInIntervalUseCase;
import com.aura8.general_backend.clean_arch.core.domain.MonthData;
import com.aura8.general_backend.clean_arch.infrastructure.enums.DirectionEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/insights/finance")
public class FinanceController {
    private final GetDashboardInfosUseCase getDashboardInfosUseCase;
    private final GetMonthDataInIntervalUseCase getMonthDataInIntervalUseCase;

    public FinanceController(GetDashboardInfosUseCase getDashboardInfosUseCase, GetMonthDataInIntervalUseCase getMonthDataInIntervalUseCase) {
        this.getDashboardInfosUseCase = getDashboardInfosUseCase;
        this.getMonthDataInIntervalUseCase = getMonthDataInIntervalUseCase;
    }

    @GetMapping("/dashboard")
    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Obter dashboard financeiro",
            description = "Retorna os dados consolidados do dashboard financeiro da empresa"
    )
    @ApiResponse(responseCode = "200", description = "Dashboard retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Dashboard não encontrado")
    public ResponseEntity<DashboardInfosDto> getFinanceDashboard() {
        DashboardInfosDto dashboardInfos = getDashboardInfosUseCase.getDashboardInfos();
        return ResponseEntity.ok(dashboardInfos);
    }

    @GetMapping("/historico")
    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Obter histórico financeiro",
            description = "Retorna o histórico financeiro mensal da empresa"
    )
    @ApiResponse(responseCode = "200", description = "Histórico financeiro retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Histórico financeiro não encontrado")
    public ResponseEntity<List<MonthData>> getHistoricoFinanceiro(
            @RequestParam(required = false, defaultValue = "-1") Integer startMonth,
            @RequestParam(required = false, defaultValue = "-1") Integer endMonth
    ) {
        List<MonthData> monthDataPage = getMonthDataInIntervalUseCase.getMonthDataInInterval(startMonth, endMonth);
        return ResponseEntity.ok(monthDataPage);
    }
}
