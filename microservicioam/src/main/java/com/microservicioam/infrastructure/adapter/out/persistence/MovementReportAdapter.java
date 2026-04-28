package com.microservicioam.infrastructure.adapter.out.persistence;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.microservicioam.application.port.out.ReportRepositoryPort;
import com.microservicioam.domain.models.Movement;
import com.microservicioam.infrastructure.adapter.out.persistence.mapper.MovementEntityMapper;
import com.microservicioam.infrastructure.adapter.out.persistence.repository.R2DBCMovementRepository;

import reactor.core.publisher.Flux;

@Repository
public class MovementReportAdapter implements ReportRepositoryPort{
    private final R2DBCMovementRepository repo;
    private final MovementEntityMapper mapper;

    public MovementReportAdapter(R2DBCMovementRepository repo, MovementEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
public Flux<Movement> findMovementsByAccountAndDateRange(String accountNumber, LocalDateTime start, LocalDateTime end) {
    return repo.findMovementsForReport(accountNumber, start, end)
            .map(mapper::toDomain);
}
}
