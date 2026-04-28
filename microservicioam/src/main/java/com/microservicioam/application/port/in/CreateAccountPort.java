package com.microservicioam.application.port.in;

import com.microservicioam.domain.dto.CreateAccountDTO;

import reactor.core.publisher.Mono;

public interface CreateAccountPort {
    Mono<Void> createAccount(CreateAccountDTO createAccountDTO);
}
