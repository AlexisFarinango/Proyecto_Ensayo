package com.microservicioam.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.microservicioam.application.exception.BadRequestException;
import com.microservicioam.application.exception.CustomerNotFoundException;
import com.microservicioam.application.mapper.report.ReportMapper;
import com.microservicioam.application.port.in.GetAccountStatementReportPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.application.port.out.CustomerValidationPort;
import com.microservicioam.application.port.out.ReportRepositoryPort;
import com.microservicioam.domain.dto.ReportResponseDTO;
import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAccountStatementReportService implements GetAccountStatementReportPort {

    private final ReportRepositoryPort reportRepositoryPort;
    private final AccountRepositoryPort accountRepository;
    private final CustomerValidationPort customerValidationPort;
    private final ReportMapper reportMapper;

    public GetAccountStatementReportService(ReportRepositoryPort reportRepositoryPort,
            AccountRepositoryPort accountRepository, CustomerValidationPort customerValidationPort,
            ReportMapper reportMapper) {
        this.reportRepositoryPort = reportRepositoryPort;
        this.accountRepository = accountRepository;
        this.customerValidationPort = customerValidationPort;
        this.reportMapper = reportMapper;

    }

    @Override
    public Mono<ReportResponseDTO> getReport(String customerId, LocalDate startDate, LocalDate endDate) {
        if (customerId == null || customerId.isBlank()) {
            return Mono.error(new BadRequestException("client-id cannot be null or blank"));
        }
        if (startDate == null || endDate == null) {
            return Mono.error(new BadRequestException("startDate and endDate are required"));
        }
        if (endDate.isBefore(startDate)) {
            return Mono.error(new BadRequestException("endDate cannot be before startDate"));
        }
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return customerValidationPort.existsCustomerByIdentification(customerId)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new CustomerNotFoundException(customerId));
                    }
                    return accountRepository.findByCustomerIdentification(customerId)
                            .collectList()
                            .flatMap(accounts -> buildReport(customerId, startDate, endDate, accounts, start, end));
                });
    }

    private Mono<ReportResponseDTO> buildReport(String customerId, LocalDate startDate, LocalDate endDate,
            List<Account> accounts, LocalDateTime start, LocalDateTime end) {
        return Flux.fromIterable(accounts)
                .filter(account -> account.getNumber() != null && !account.getNumber().isBlank())
                .flatMap(account -> reportRepositoryPort
                        .findMovementsByAccountAndDateRange(account.getNumber(), start, end)
                        .map(reportMapper::toMovementDTO)
                        .collectList()
                        .map(movements -> reportMapper.toAccountDTO(account, movements)))
                .collectList()
                .map(accountReports -> new ReportResponseDTO(customerId, startDate, endDate, accountReports));

    }
}
