package com.microservicioam.infrastructure.adapter.in;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicioam.application.port.in.GetAccountStatementReportPort;
import com.microservicioam.domain.dto.ReportResponseDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final GetAccountStatementReportPort getAccountStatementReportPort;

    public ReportController(GetAccountStatementReportPort getAccountStatementReportPort) {
        this.getAccountStatementReportPort = getAccountStatementReportPort;
    }

    @GetMapping("/{customerId}")
    public Mono<ReportResponseDTO> getReport(@PathVariable String customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return getAccountStatementReportPort.getReport(customerId, startDate, endDate);
    }

}
