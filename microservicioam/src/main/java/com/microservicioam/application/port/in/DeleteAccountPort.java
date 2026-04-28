package com.microservicioam.application.port.in;

import reactor.core.publisher.Mono;

public interface DeleteAccountPort {
    Mono<Void> deleteAccount(String number);
}
