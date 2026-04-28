package com.microservicioam.infrastructure.adapter.out.persistence.repository;

import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.microservicioam.infrastructure.adapter.out.persistence.entity.MovementEntity;

import reactor.core.publisher.Flux;

public interface R2DBCMovementRepository extends ReactiveCrudRepository<MovementEntity, Long> {
    @Query("""
        SELECT id, movement_date, type, value, balance, account_number
        FROM movements
        WHERE account_number = :accountNumber
          AND movement_date >= :startDate
          AND movement_date < :endDate
    """)
    Flux<MovementEntity> findMovementsForReport(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
}
