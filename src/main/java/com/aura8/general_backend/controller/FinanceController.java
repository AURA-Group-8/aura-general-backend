package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.finance.DashboardDto;
import com.aura8.general_backend.dtos.finance.MonthDataDto;
import com.aura8.general_backend.dtos.finance.MonthDataHistoryDto;
import com.aura8.general_backend.service.FinanceService;
import com.aura8.general_backend.service.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/insights/finance")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
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
    public ResponseEntity<DashboardDto> getFinanceDashboard() {
        DashboardDto dashboardDto = financeService.generateFinanceDashboard();
        return ResponseEntity.ok(dashboardDto);
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
    public ResponseEntity<List<MonthDataHistoryDto>> getHistoricoFinanceiro() {
        return ResponseEntity.ok(financeService.getHistoricoFinanceiro());
    }
}
