package com.microservicioam.application.port.out;

import java.time.LocalDateTime;

import com.microservicioam.domain.models.Movement;

import reactor.core.publisher.Flux;

public interface ReportRepositoryPort {
    Flux<Movement> findMovementsByAccountAndDateRange(String accountNumber, LocalDateTime start, LocalDateTime end);

}
