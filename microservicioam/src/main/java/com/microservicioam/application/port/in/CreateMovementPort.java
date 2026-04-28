package com.microservicioam.application.port.in;

import com.microservicioam.domain.dto.CreateMovementDTO;

import reactor.core.publisher.Mono;

public interface CreateMovementPort {
    Mono<Void> createMovement(CreateMovementDTO createMovementDTO);
}
