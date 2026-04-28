package com.microservicioam.application.port.out;

import reactor.core.publisher.Mono;

public interface CustomerValidationPort {
    Mono<Boolean> existsCustomerByIdentification(String customerIdentification);
}
