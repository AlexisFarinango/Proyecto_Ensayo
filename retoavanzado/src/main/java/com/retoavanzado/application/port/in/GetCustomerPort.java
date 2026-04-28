package com.retoavanzado.application.port.in;

import com.retoavanzado.domain.models.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetCustomerPort {
    Mono<Customer> getCustomerByIdentification(String identification);
    Flux<Customer> getAllCustomers();
}
