package com.microservicioam.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.microservicioam.application.port.out.MovementRepositoryPort;
import com.microservicioam.domain.models.Movement;
import com.microservicioam.infrastructure.adapter.out.persistence.mapper.MovementEntityMapper;
import com.microservicioam.infrastructure.adapter.out.persistence.repository.R2DBCMovementRepository;

import reactor.core.publisher.Mono;

@Repository
public class MovementAdapter implements MovementRepositoryPort{
    public final R2DBCMovementRepository r2dbcMovementRepository;
    private final MovementEntityMapper mapper;

    public MovementAdapter(R2DBCMovementRepository r2dbcMovementRepository, MovementEntityMapper mapper) {
        this.r2dbcMovementRepository = r2dbcMovementRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> saveMovement(Movement movement) {
        return r2dbcMovementRepository.save(mapper.toEntity(movement)).then();
    }

}
