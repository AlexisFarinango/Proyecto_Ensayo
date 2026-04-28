package com.microservicioam.application.port.in;

import com.microservicioam.domain.dto.UpdateAccountDTO;

import reactor.core.publisher.Mono;

public interface UpdateAccountPort {
    Mono<Void> updateAccount(String number, UpdateAccountDTO updateAccountDTO);
}
