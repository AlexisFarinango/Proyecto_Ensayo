package com.microservicioam.infrastructure.adapter.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicioam.application.port.in.CreateMovementPort;
import com.microservicioam.domain.dto.CreateMovementDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movements")
public class MovementController {
    private final CreateMovementPort createMovementPort;

    public MovementController(CreateMovementPort createMovementPort) {
        this.createMovementPort = createMovementPort;
    }

    @PostMapping
    public Mono<Void> createMovement(@RequestBody CreateMovementDTO createMovementDTO) {
        return createMovementPort.createMovement(createMovementDTO);
    }
}
