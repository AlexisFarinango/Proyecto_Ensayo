package com.microservicioam.application.port.in;

import java.time.LocalDate;

import com.microservicioam.domain.dto.ReportResponseDTO;

import reactor.core.publisher.Mono;

public interface GetAccountStatementReportPort {
    Mono<ReportResponseDTO> getReport(String customerId, LocalDate startDate, LocalDate endDate);
}
