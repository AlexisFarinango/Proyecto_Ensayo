package com.microservicioam.application.port.out;

import com.microservicioam.domain.models.Movement;
import reactor.core.publisher.Mono;

public interface MovementRepositoryPort {
    Mono<Void> saveMovement(Movement movement);

}
